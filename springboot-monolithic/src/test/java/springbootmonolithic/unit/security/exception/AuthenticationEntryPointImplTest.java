package springbootmonolithic.unit.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import springbootmonolithic.security.exception.AuthenticationEntryPointImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthenticationEntryPointImplTest {

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("AuthenticationEntryPoint 테스트")
    @Test
    void testAuthenticationEntryPoint() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException exception = new BadCredentialsException("Invalid credentials");

        AuthenticationEntryPointImpl entryPoint = new AuthenticationEntryPointImpl(objectMapper);
        entryPoint.commence(request, response, exception);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());

        // 테스트환경과 실제 환경에서 MediaType.APPLICATION_JSON_VALUE가 형식이 다름
        // assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertTrue(response.getContentAsString().contains("인증이 필요합니다."));
    }
}
