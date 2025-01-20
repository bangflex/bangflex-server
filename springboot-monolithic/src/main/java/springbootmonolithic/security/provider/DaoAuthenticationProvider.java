package springbootmonolithic.security.provider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import springbootmonolithic.common.util.JwtUtil;
import springbootmonolithic.domain.member.query.service.AuthQueryService;

@Slf4j
@Component
public class DaoAuthenticationProvider implements AuthenticationProvider {
    private final AuthQueryService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final HttpServletResponse response;

    public DaoAuthenticationProvider(
            AuthQueryService authService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtUtil jwtUtil,
            HttpServletResponse response
    ) {
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.response = response;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getPrincipal().toString();
        String loginPassword = authentication.getCredentials().toString();

        UserDetails savedUser = authService.loadUserByUsername((authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName());

        if (!bCryptPasswordEncoder.matches(loginPassword, savedUser.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        } else {
            Authentication authenticationResult = new UsernamePasswordAuthenticationToken(savedUser, savedUser.getPassword(), savedUser.getAuthorities());

//            String refreshToken = jwtUtil.generateRefreshToken(authenticationResult);
            String accessToken = jwtUtil.generateAccessToken(authenticationResult);

//            refreshTokenService.saveRefreshToken(loginId, refreshToken);
            response.setHeader("Authorization", "Bearer " + accessToken);
//            response.setHeader("Refresh-Token", refreshToken);

            return authenticationResult;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
