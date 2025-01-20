package springbootmonolithic.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import springbootmonolithic.domain.member.query.service.AuthQueryService;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final Environment environment;
    private final AuthQueryService authService;

    @Autowired
    public JwtUtil(
            @Value("${token.secret}") String secretKey,
            Environment environment,
            AuthQueryService authService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.environment = environment;
        this.authService = authService;
    }

    public String generateAccessToken(Authentication authentication) {
        return createToken(
                setClaims(authentication),
                environment.getProperty("token.access.expiration_time")
        );
    }

    private Claims setClaims(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put(
                "authorities",
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
        );
        return claims;
    }

    private String createToken(Claims claims, String tokenExpiredAt) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + Long.parseLong(
                                        Objects.requireNonNull(tokenExpiredAt)
                                )
                        )
                )
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid token {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired token {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported token {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Empty token {}", e.getMessage());
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Collection<? extends GrantedAuthority> authorities = null;

        if (claims.get("authorities") != null) {
            authorities = Arrays.stream(
                            claims.get("authorities").toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .split(", ")
                    )
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            log.error("No authorities found in token");
            throw new IllegalArgumentException("No authorities found in token");
        }
        UserDetails savedUser = authService.loadUserByUsername(getLoginId(token));
        return new UsernamePasswordAuthenticationToken(
                savedUser,
                savedUser.getPassword(),
                authorities
        );
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getLoginId(String token)  {
        return parseClaims(token).getSubject();
    }
}
