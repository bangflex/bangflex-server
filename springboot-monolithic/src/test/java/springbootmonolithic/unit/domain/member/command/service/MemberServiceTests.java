package springbootmonolithic.unit.domain.member.command.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootmonolithic.common.domain.member.role.RoleType;
import springbootmonolithic.domain.member.command.application.dto.request.SignUpRequestDTO;
import springbootmonolithic.domain.member.command.application.service.MemberServiceImpl;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.MemberRole;
import springbootmonolithic.domain.member.command.domain.repository.MemberRoleRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootmonolithic.domain.member.command.domain.repository.MemberRepository;
import springbootmonolithic.domain.member.query.service.MemberQueryServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MemberServiceTests {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberRoleRepository memberRoleRepository;

    @Mock
    private MemberQueryServiceImpl memberQueryService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    @DisplayName("회원가입")
    @Test
    void shouldReturnTrueWhenSignUpSuccessful() {

        // given
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO(
                "test@example.com",
                "password",
                "nickname",
                "profile image"
        );
        Member expectedMember = new Member(
                4,
                signUpRequestDTO.getEmail(),
                "encodedPassword",
                signUpRequestDTO.getNickname(),
                signUpRequestDTO.getImage()
        );
        MemberRole expectedMemberRole = MemberRole.builder()
                        .memberCode(expectedMember.getCode())
                        .roleCode(RoleType.USER.getOrder())
                        .build();

        when(passwordEncoder.encode(signUpRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(expectedMember);

        // when
        memberService.signUp(signUpRequestDTO);

        // then
        verify(memberQueryService).validateEmail(signUpRequestDTO.getEmail());
        verify(passwordEncoder).encode(signUpRequestDTO.getPassword());
        verify(memberRepository).save(argThat(member ->
                member.getEmail().equals(expectedMember.getEmail()) &&
                        member.getPassword().equals(expectedMember.getPassword()) &&
                        member.getNickname().equals(expectedMember.getNickname()) &&
                        member.getImage().equals(expectedMember.getImage())
        ));
        verify(memberRoleRepository).save(argThat(memberRole ->
                memberRole.getMemberCode() == expectedMemberRole.getMemberCode() &&
                        memberRole.getRoleCode() == expectedMemberRole.getRoleCode()
        ));
    }

    @DisplayName("회원 비활성화")
    @Test
    void deactivateMemberBy_Success() {
        // Given
        Member mockMember = new Member(
                1,
                true,
                "createdAt",
                "updatedAt",
                "test@test.com",
                "testpw",
                "testnickname",
                "testImage"
        );

        Member expectedMember = new Member(
                1,
                false,
                "createdAt",
                "updatedAt",
                "test@test.com",
                "testpw",
                "testnickname",
                "testImage"
        );

        when(memberRepository.findByCodeAndActiveTrue(mockMember.getCode()))
                .thenReturn(Optional.of(expectedMember));

        // When
        memberService.deactivateMemberBy(mockMember.getCode());

        // Then
        assertThat(mockMember.getCode()).isEqualTo(expectedMember.getCode());
    }
}
