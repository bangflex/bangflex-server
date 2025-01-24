package springbootmonolithic.unit.security.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import springbootmonolithic.common.util.JwtUtil;
import springbootmonolithic.security.provider.AccessTokenAuthenticationProvider;
import springbootmonolithic.security.token.JwtAuthenticationAccessToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenAuthenticationProviderTests {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtAuthenticationAccessToken tokenAuthentication; // Authentication 구현체

    private AccessTokenAuthenticationProvider provider;

    @BeforeEach
    void setUp() {
        provider = new AccessTokenAuthenticationProvider(jwtUtil);
    }

    @DisplayName("AccessTokenAuthenticationProvider가 provider manager로 관리되는지 확인")
    @Test
    void testSupports() {
        assertThat(provider.supports(JwtAuthenticationAccessToken.class)).isTrue();
        assertThat(provider.supports(String.class)).isFalse();
    }

    @DisplayName("AccessTokenAuthenticationProvider가 토큰을 검증하고 Authentication 객체를 반환하는지 확인")
    @Test
    void testAuthenticate_ValidToken() {

        // given
        String token = "validToken";
        when(tokenAuthentication.getCredentials()).thenReturn(token);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        Authentication expectedAuth = mock(Authentication.class);
        when(jwtUtil.getAuthentication(token)).thenReturn(expectedAuth);

        // when
        Authentication result = provider.authenticate(tokenAuthentication);

        // then
        assertThat(result).isEqualTo(expectedAuth);
        verify(jwtUtil).isTokenValid(token);
        verify(jwtUtil).getAuthentication(token);
    }

    @DisplayName("AccessTokenAuthenticationProvider가 유효하지 않은 토큰을 받았을 때 IllegalArgumentException을 던지는지 확인")
    @Test
    void testAuthenticate_InvalidToken() {

        // given
        String token = "invalidToken";
        when(tokenAuthentication.getCredentials()).thenReturn(token);
        when(jwtUtil.isTokenValid(token)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> provider.authenticate(tokenAuthentication))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 엑세스 토큰입니다.");

        verify(jwtUtil).isTokenValid(token);    // 토큰 유효성 검증 메서드 호출 횟수 확인 (1회)
    }
}
