package springbootmonolithic.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import springbootmonolithic.security.provider.ProviderManager;
import springbootmonolithic.security.token.JwtAuthenticationAccessToken;

import java.io.IOException;

@Slf4j
public class JwtAccessTokenFilter extends OncePerRequestFilter {
    private final ProviderManager providerManager;

    public JwtAccessTokenFilter(ProviderManager providerManager) {
        this.providerManager = providerManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        // 헤더가 있는지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            Authentication authentication = providerManager.authenticate(
                    new JwtAuthenticationAccessToken(authorizationHeader.replace("Bearer ", ""))
            );

            // 인증 성공 시 SecurityContext에 설정
            if (authentication != null && authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
