package springbootmonolithic.security.unit.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class DaoAuthenticationFilterTests {

    @DisplayName("로그인 - 성공")
    @Test
    void loginSuccess() {
        // given
        // when
        // then
        assertThat(true).isFalse();
    }

    @DisplayName("로그인 - 실패")
    @Test
    void loginFail() {
        // given
        // when
        // then
        assertThat(true).isFalse();
    }
}
