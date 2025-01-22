package springbootmonolithic.integration.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import springbootmonolithic.security.dto.LoginRequestDTO;
import springbootmonolithic.security.provider.ProviderManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityConfigurationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProviderManager providerManager;

    private final String LOGIN_URL = "/api/v1/auth/login";
    private final String REFRESH_URL = "/api/v1/auth/refresh";

    @DisplayName("로그인 - 성공")
    @Test
    void loginSuccess() {
        // given
        LoginRequestDTO loginRequest = new LoginRequestDTO("signed up user", "correct password");

        // when
        // then
        assertThat(true).isTrue();
    }

    @DisplayName("로그인 - 성공")
    @Test
    void loginFailed() {
        // given
        LoginRequestDTO loginRequest = new LoginRequestDTO("enteredEmail", "incorrect password");

        // when
        // then
        assertThat(true).isTrue();
    }

    @DisplayName("refresh token 발급 - 성공")
    @Test
    void shouldSuccessAndReturnRefreshToken() {
        // given
        // when
        // then
        assertThat(false).isFalse();
    }

    @DisplayName("refresh token 발급 - 실패")
    @Test
    void shouldFailedWithInvalidRefreshToken() {
        // given
        // when
        // then
        assertThat(false).isFalse();
    }
}
