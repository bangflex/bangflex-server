package springbootmonolithic.unit.security.provider;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import springbootmonolithic.common.util.JwtUtil;
import springbootmonolithic.security.domain.service.AuthQueryService;
import springbootmonolithic.security.domain.service.RefreshTokenService;
import springbootmonolithic.security.provider.RefreshTokenAuthenticationProvider;
import springbootmonolithic.security.token.JwtAuthenticationRefreshToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenAuthenticationProviderTests {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AuthQueryService authService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private JwtAuthenticationRefreshToken refreshTokenAuth;

    private RefreshTokenAuthenticationProvider provider;

    @BeforeEach
    void setUp() {
        provider = new RefreshTokenAuthenticationProvider(
                jwtUtil,
                refreshTokenService,
                authService,
                response
        );
    }

    @Test
    void testSupports() {
        assertThat(provider.supports(JwtAuthenticationRefreshToken.class)).isTrue();
        assertThat(provider.supports(Authentication.class)).isFalse();
    }

    @DisplayName("access token 검증 테스트")
    @Test
    void testAuthenticate_Success() {

        // given
        String token = "validRefreshToken";
        String loginId = "testUser";
        when(refreshTokenAuth.getCredentials()).thenReturn(token);
        when(jwtUtil.getLoginId(token)).thenReturn(loginId);

        when(refreshTokenService.checkRefreshTokenInRedis(loginId, token)).thenReturn(true);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);

        UserDetails userDetails = mock(UserDetails.class);
        when(authService.loadUserByUsername(loginId)).thenReturn(userDetails);

        when(jwtUtil.generateAccessToken(any())).thenReturn("newAccessToken");

        Authentication expectedAuth = mock(Authentication.class);
        when(jwtUtil.getAuthentication(token)).thenReturn(expectedAuth);

        // when
        Authentication result = provider.authenticate(refreshTokenAuth);

        // then
        assertThat(result).isEqualTo(expectedAuth);
        verify(response).setHeader("Authorization", "Bearer newAccessToken");
    }

    @DisplayName("refresh token이 redis에 없는 경우")
    @Test
    void testAuthenticate_RefreshTokenNotFoundInRedis() {

        // given
        String token = "notFoundToken";
        String loginId = "testUser";
        when(refreshTokenAuth.getCredentials()).thenReturn(token);
        when(jwtUtil.getLoginId(token)).thenReturn(loginId);

        when(refreshTokenService.checkRefreshTokenInRedis(loginId, token)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> provider.authenticate(refreshTokenAuth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Refresh token not found");
    }

    @DisplayName("access token이 유효하지 않은 경우")
    @Test
    void testAuthenticate_InvalidToken() {
        // given
        String token = "invalidToken";
        String loginId = "testUser";
        when(refreshTokenAuth.getCredentials()).thenReturn(token);
        when(jwtUtil.getLoginId(token)).thenReturn(loginId);

        when(refreshTokenService.checkRefreshTokenInRedis(loginId, token)).thenReturn(true);
        when(jwtUtil.isTokenValid(token)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> provider.authenticate(refreshTokenAuth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid token");
    }

    @DisplayName("member 조회 실패")
    @Test
    void testAuthenticate_UserNotFound() {
        // given
        String token = "validRefreshToken";
        String loginId = "testUser";
        when(refreshTokenAuth.getCredentials()).thenReturn(token);
        when(jwtUtil.getLoginId(token)).thenReturn(loginId);
        when(refreshTokenService.checkRefreshTokenInRedis(loginId, token)).thenReturn(true);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(authService.loadUserByUsername(loginId)).thenThrow(new RuntimeException("user not found"));

        // when & then
        assertThatThrownBy(() -> provider.authenticate(refreshTokenAuth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user not found");
    }
}
