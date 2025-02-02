package springbootmonolithic.security.provider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import springbootmonolithic.common.util.JwtUtil;
import springbootmonolithic.security.domain.service.AuthQueryService;
import springbootmonolithic.security.domain.service.RefreshTokenService;
import springbootmonolithic.security.token.JwtAuthenticationRefreshToken;

@Slf4j
@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final AuthQueryService authService;
    private final HttpServletResponse response;

    public RefreshTokenAuthenticationProvider(
            JwtUtil jwtUtil,
            RefreshTokenService refreshTokenService,
            AuthQueryService authService,
            HttpServletResponse response
    ) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.authService = authService;
        this.response = response;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String loginId = jwtUtil.getLoginId(token);

        if (!refreshTokenService.checkRefreshTokenInRedis(loginId, token)) {
            throw new IllegalArgumentException("Refresh token not found");
        }

        if (!jwtUtil.isTokenValid(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        try {
            UserDetails savedUser = authService.loadUserByUsername(loginId);
            Authentication authResult =  new UsernamePasswordAuthenticationToken(savedUser, savedUser.getPassword(), savedUser.getAuthorities());
            String accessToken = jwtUtil.generateAccessToken(authResult);
            response.setHeader("Authorization", "Bearer " + accessToken);
            return jwtUtil.getAuthentication(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("user not found", e);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationRefreshToken.class.isAssignableFrom(authentication);
    }
}
