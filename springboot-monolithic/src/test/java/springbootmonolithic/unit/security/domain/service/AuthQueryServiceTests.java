package springbootmonolithic.unit.security.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import springbootmonolithic.security.domain.CustomUser;
import springbootmonolithic.security.domain.dto.MemberDTO;
import springbootmonolithic.security.domain.dto.RoleDTO;
import springbootmonolithic.security.domain.mapper.AuthMapper;
import springbootmonolithic.security.domain.service.AuthQueryServiceImpl;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthQueryServiceTests {

    @Mock
    private AuthMapper authMapper;

    private AuthQueryServiceImpl authQueryService;

    @BeforeEach
    void setUp() {
        authQueryService = new AuthQueryServiceImpl(authMapper);
    }

    @DisplayName("사용자 조회 테스트 - 성공")
    @Test
    void testLoadUserByUsername_Success() {

        // given
        String email = "test@example.com";
        MemberDTO mockMember = new MemberDTO();
        mockMember.setCode(1);
        mockMember.setEmail(email);
        mockMember.setPassword("encodedPassword");
        mockMember.setNickname("TestUser");

        HashSet<RoleDTO> roleSet = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole("ROLE_USER");
        roleSet.add(roleDTO);

        mockMember.setRoleDTO(roleSet);

        when(authMapper.selectMemberByEmailWithAuthorities(email)).thenReturn(mockMember);

        // when
        UserDetails userDetails = authQueryService.loadUserByUsername(email);

        // then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails).isInstanceOf(CustomUser.class);

        CustomUser customUser = (CustomUser) userDetails;
        assertThat(customUser.getUsername()).isEqualTo(email);
        assertThat(customUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(customUser.getNickname()).isEqualTo("TestUser");
    }

    @DisplayName("사용자 조회 테스트 - 사용자 없음")
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // given
        String email = "notfound@example.com";
        when(authMapper.selectMemberByEmailWithAuthorities(email)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> authQueryService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("사용자를 찾을 수 없습니다.");
    }
}
