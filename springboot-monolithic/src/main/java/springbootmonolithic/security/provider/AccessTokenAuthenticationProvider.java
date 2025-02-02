package springbootmonolithic.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import springbootmonolithic.common.util.JwtUtil;
import springbootmonolithic.security.token.JwtAuthenticationAccessToken;

@Slf4j
@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;

    @Autowired
    public AccessTokenAuthenticationProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        if (jwtUtil.isTokenValid(token)) {
            return jwtUtil.getAuthentication(token);
        }
        log.error("유효하지 않은 엑세스 토큰입니다.");
        throw new IllegalArgumentException("유효하지 않은 엑세스 토큰입니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationAccessToken.class.isAssignableFrom(authentication);
    }
}
