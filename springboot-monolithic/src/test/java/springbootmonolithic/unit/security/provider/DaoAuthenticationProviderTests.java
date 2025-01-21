package springbootmonolithic.unit.security.provider;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootmonolithic.common.util.JwtUtil;
import springbootmonolithic.security.domain.service.AuthQueryService;
import springbootmonolithic.security.domain.service.RefreshTokenService;
import springbootmonolithic.security.provider.DaoAuthenticationProvider;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoAuthenticationProviderTests {

    @Mock
    private AuthQueryService authService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RefreshTokenService refreshTokenService;

    private DaoAuthenticationProvider daoAuthenticationProvider;

    @BeforeEach
    void setUp() {
        daoAuthenticationProvider = new DaoAuthenticationProvider(
                authService,
                bCryptPasswordEncoder,
                jwtUtil,
                response,
                refreshTokenService
        );
    }

    @DisplayName("DaoAuthenticationProvider가 provider manager로 관리되는지 확인")
    @Test
    void testSupports() {
        assertThat(daoAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class)).isTrue();
        assertThat(daoAuthenticationProvider.supports(Authentication.class)).isFalse();
    }

    @DisplayName("DaoAuthenticationProvider의 access token 토큰 검증 테스트")
    @Test
    void testAuthenticate_Success() {

        // given
        String loginId = "testUser";
        String rawPassword = "rawPass";

        Authentication auth = new UsernamePasswordAuthenticationToken(loginId, rawPassword);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn("encodedPass");
        when(userDetails.getAuthorities()).thenReturn(null);

        when(authService.loadUserByUsername(loginId)).thenReturn(userDetails);
        when(bCryptPasswordEncoder.matches(rawPassword, "encodedPass")).thenReturn(true);

        when(jwtUtil.generateRefreshToken(any())).thenReturn("refreshToken");
        when(jwtUtil.generateAccessToken(any())).thenReturn("accessToken");

        // when
        Authentication result = daoAuthenticationProvider.authenticate(auth);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getPrincipal()).isEqualTo(userDetails);
        assertThat(result.isAuthenticated()).isTrue();

        verify(refreshTokenService).saveRefreshToken(eq(loginId), eq("refreshToken"));
        verify(response).setHeader("Authorization", "Bearer accessToken");
        verify(response).setHeader("Refresh-Token", "refreshToken");
    }

    @Test
    void testAuthenticate_BadCredentials() {
        // given
        String loginId = "testUser";
        String rawPassword = "wrongPass";

        Authentication auth = new UsernamePasswordAuthenticationToken(loginId, rawPassword);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn("encodedPass");

        when(authService.loadUserByUsername(loginId)).thenReturn(userDetails);
        when(bCryptPasswordEncoder.matches(rawPassword, "encodedPass")).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> daoAuthenticationProvider.authenticate(auth))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Bad credentials");

        // response 헤더 등이 설정되지 않았는지 확인
        verify(jwtUtil, never()).generateRefreshToken(any());
        verify(response, never()).setHeader(anyString(), anyString());
        verify(response, never()).setHeader(anyString(), anyString());
    }
}
