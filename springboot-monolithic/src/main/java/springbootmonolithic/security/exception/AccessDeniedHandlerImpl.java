package springbootmonolithic.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import springbootmonolithic.common.response.ErrorResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private static final int STATUS_CODE_403 = HttpServletResponse.SC_FORBIDDEN;
    private static final String FORBIDDEN_ERROR_MESSAGE = "접근 권한이 없습니다.";
    private final ObjectMapper objectMapper;

    @Autowired
    public AccessDeniedHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error(FORBIDDEN_ERROR_MESSAGE);

        // 응답 헤더 및 상태 코드 설정
        response.setStatus(STATUS_CODE_403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ErrorResponse 객체를 JSON으로 변환 후 응답에 작성
        response.getWriter().write(
            objectMapper.writeValueAsString(
                    ErrorResponse.builder()
                    .message(FORBIDDEN_ERROR_MESSAGE)
                    .timestamp(LocalDateTime.now())
                    .build()
            )
        );
    }
}
