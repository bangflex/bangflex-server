package springbootmonolithic.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final Environment environment;

    @Autowired
    public JwtUtil(
            @Value("${token.secret}") String secretKey,
            Environment environment
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.environment = environment;
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
}
