package springbootmonolithic.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.security.provider.ProviderManager;
import springbootmonolithic.security.token.JwtAuthenticationRefreshToken;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class JwtRefreshTokenFilter extends OncePerRequestFilter {
    private final ProviderManager providerManager;
    private final ObjectMapper objectMapper;
    private final AntPathRequestMatcher refreshTokenRequestMatcher;

    public JwtRefreshTokenFilter(
            ProviderManager providerManager,
            ObjectMapper objectMapper
    ) {
        this.providerManager = providerManager;
        this.objectMapper = objectMapper;
        this.refreshTokenRequestMatcher = new AntPathRequestMatcher("/api/v1/auth/refresh", "POST");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (refreshTokenRequestMatcher.matches(request)) {
            String authenticationHeader = request.getHeader("Refresh-Token");

            if (authenticationHeader != null) {
                providerManager.authenticate(
                        new JwtAuthenticationRefreshToken(authenticationHeader)
                );

                // 응답 헤더 및 상태 코드 설정
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // ResponseMessage 객체를 JSON으로 변환 후 응답에 작성
                response.getWriter().write(
                        objectMapper.writeValueAsString(
                                SuccessResponse.<Boolean>builder()
                                        .message("성공적으로 토큰이 갱신되었습니다.")
                                        .result(true)
                                        .timestamp(LocalDateTime.now())
                                        .build()
                        )
                );
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
