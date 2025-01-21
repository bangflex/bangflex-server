package springbootmonolithic.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.security.dto.LoginRequestDTO;
import springbootmonolithic.security.provider.ProviderManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
public class DaoAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String LOGIN_URL = "/api/v1/auth/login";
    private final ProviderManager providerManager;
    private final ObjectMapper objectMapper;

    public DaoAuthenticationFilter(
            ProviderManager providerManager,
            ObjectMapper objectMapper
    ) {
        this.providerManager = providerManager;
        this.objectMapper = objectMapper;
        this.setFilterProcessesUrl(LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDTO loginRequestDTO = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
            return providerManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getLoginId(), loginRequestDTO.getPassword(), new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse login request", e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {

        // 응답 헤더 및 상태 코드 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ResponseMessage 객체를 JSON으로 변환 후 응답에 작성
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        SuccessResponse.<Boolean>builder()
                                .message("로그인 성공")
                                .result(true)
                                .timestamp(LocalDateTime.now())
                                .build()
                )
        );
    }
}
