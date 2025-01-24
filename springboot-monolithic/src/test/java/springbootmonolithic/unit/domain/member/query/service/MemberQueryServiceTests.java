package springbootmonolithic.unit.domain.member.query.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootmonolithic.domain.member.query.dto.MemberInformationDTO;
import springbootmonolithic.domain.member.query.dto.RoleDTO;
import springbootmonolithic.domain.member.query.mapper.MemberMapper;
import springbootmonolithic.domain.member.query.service.MemberQueryServiceImpl;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTests {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberQueryServiceImpl memberQueryService;

    @DisplayName("이메일 중복 체크 - 이메일 중복인 경우")
    @Test
    void shouldReturnTrueWhenEmailIsDuplicated() {

        // given
        String duplicatedEmail = "test@test.com";
        when(memberMapper.isExistsByEmail(duplicatedEmail)).thenReturn(true);

        // when
        boolean result = memberQueryService.checkEmailDuplicated(duplicatedEmail);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("이메일 중복 체크 - 이메일 중복이 아닌 경우")
    @Test
    void shouldReturnFalseWhenEmailIsNotDuplicated() {

        // given
        String nonDuplicatedEmail = "test@test.com";
        when(memberMapper.isExistsByEmail(nonDuplicatedEmail)).thenReturn(false);

        // when
        boolean result = memberQueryService.checkEmailDuplicated(nonDuplicatedEmail);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이메일로 회원 정보 조회")
    @Test
    void shouldReturnMemberInformationByEmail() {
        Set<RoleDTO> roleSet  = new HashSet<>();
        roleSet.add(new RoleDTO("ROLE_USER"));
        roleSet.add(new RoleDTO("ROLE_ADMIN"));
        MemberInformationDTO memberInformationDTO = new MemberInformationDTO(
                1,
                "2025-01-01",
                "2021-01-01",
                "test@gmail.com",
                "test",
                "test.jpg",
                roleSet
        );

        // given
        String email = "test@gmail.com";
        when(memberMapper.selectMemberInformationByEmail(email)).thenReturn(memberInformationDTO);

        // when
        MemberInformationDTO result = memberQueryService.getMemberInformationByEmail(email);

        // then
        assertThat(result).isEqualTo(memberInformationDTO);
    }
}
