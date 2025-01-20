package springbootmonolithic.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import springbootmonolithic.common.response.ErrorResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private static final int STATUS_CODE_401 = HttpServletResponse.SC_UNAUTHORIZED;
    private static final String AUTHENTICATION_ERROR_MESSAGE = "인증이 필요합니다.";
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error(AUTHENTICATION_ERROR_MESSAGE);

        // 응답 헤더 및 상태 코드 설정
        response.setStatus(STATUS_CODE_401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ErrorResponse 객체를 JSON으로 변환 후 응답에 작성
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ErrorResponse.builder()
                                .message(AUTHENTICATION_ERROR_MESSAGE)
                                .timestamp(LocalDateTime.now())
                                .build()
                )
        );
    }
}
