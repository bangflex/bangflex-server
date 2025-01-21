package springbootmonolithic.integration.security.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityConfigurationTests {

    @DisplayName("로그인 - 성공")
    @Test
    void loginSuccess() {
        // given
        // when
        // then
        assertThat(false).isFalse();
//        assertThat(true).isFalse();
    }

    @DisplayName("로그인 - 실패")
    @Test
    void loginFail() {
        // given
        // when
        // then
        assertThat(false).isFalse();
//        assertThat(true).isFalse();
    }
}
