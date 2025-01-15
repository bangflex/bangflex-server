package springbootmonolithic.domain.unit.domain.member.query.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootmonolithic.domain.member.query.mapper.AuthMapper;
import springbootmonolithic.domain.member.query.service.AuthQueryServiceImpl;
import springbootmonolithic.exception.EmailDuplicatedException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthQueryServiceTests {

    @Mock
    private AuthMapper authMapper;

    @InjectMocks
    private AuthQueryServiceImpl authQueryService;

    @DisplayName("이메일 중복 체크 - 이메일 중복인 경우")
    @Test
    void shouldReturnTrueWhenEmailIsDuplicated() {

        // given
        String duplicatedEmail = "test@test.com";
        when(authMapper.isExistsByEmail(duplicatedEmail)).thenThrow(new EmailDuplicatedException("이미 존재하는 이메일입니다."));

        // when & then
        assertThatThrownBy(() -> authQueryService.checkEmailDuplicated(duplicatedEmail))
                .isInstanceOf(EmailDuplicatedException.class)
                .hasMessageContaining("이미 존재하는 이메일입니다.");
    }

    @DisplayName("이메일 중복 체크 - 이메일 중복이 아닌 경우")
    @Test
    void shouldReturnFalseWhenEmailIsDuplicated() {

        // given
        String nonDuplicatedEmail = "test@test.com";
        when(authMapper.isExistsByEmail(nonDuplicatedEmail)).thenReturn(false);

        // when
        boolean result = authQueryService.checkEmailDuplicated(nonDuplicatedEmail);

        // then
        assertThat(result).isFalse();
    }
}
