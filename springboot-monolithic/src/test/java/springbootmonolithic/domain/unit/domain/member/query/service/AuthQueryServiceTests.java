package springbootmonolithic.domain.unit.domain.member.query.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootmonolithic.domain.member.query.mapper.AuthMapper;
import springbootmonolithic.domain.member.query.service.AuthQueryServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        when(authMapper.isExistsByEmail(duplicatedEmail)).thenReturn(true);

        // when
        boolean result = authQueryService.checkEmailDuplicated(duplicatedEmail);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("이메일 중복 체크 - 이메일 중복이 아닌 경우")
    @Test
    void shouldReturnFalseWhenEmailIsNotDuplicated() {

        // given
        String nonDuplicatedEmail = "test@test.com";
        when(authMapper.isExistsByEmail(nonDuplicatedEmail)).thenReturn(false);

        // when
        boolean result = authQueryService.checkEmailDuplicated(nonDuplicatedEmail);

        // then
        assertThat(result).isFalse();
    }
}
