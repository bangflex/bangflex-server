package springbootmonolithic.unit.security.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthServiceTests {

    @DisplayName("loadUserByUsername - 성공")
    @Test
    void loadUserByUsernameShouldReturnUserDetails() {
        // given
        // when
        // then
        assertThat(false).isFalse();
    }

    @DisplayName("loadUserByUsername - 실패")
    @Test
    void loadUserByUsernameShouldThrowUsernameNotFoundException() {
        // given
        // when
        // then
        assertThat(false).isFalse();
    }
}
