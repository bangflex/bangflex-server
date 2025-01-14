package springbootmonolithic.domain.unit.member.command.application.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AuthService authService;


    @DisplayName("이메일 중복 체크")
    @Test
    void checkEmailDuplicateTest() {

        // given
        String duplicatedEmail = "test@test.com";
        when(memberRepository.existsByEmail(duplicatedEmail).thenReturn(true));

        // when
        boolean result = authService.isEmailDuplicated(duplicatedEmail);

        // then
        assertThat(result).isTrue();
    }
}
