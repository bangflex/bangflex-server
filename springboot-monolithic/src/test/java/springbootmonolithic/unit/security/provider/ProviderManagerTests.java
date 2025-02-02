package springbootmonolithic.unit.security.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import springbootmonolithic.security.exception.NotAuthenticatedException;
import springbootmonolithic.security.provider.ProviderManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProviderManagerTests {

    @Mock
    private AuthenticationProvider provider1;

    @Mock
    private AuthenticationProvider provider2;

    @Mock
    private Authentication authentication;

    private ProviderManager providerManager;

    @BeforeEach
    void setUp() {
        providerManager = new ProviderManager(List.of(provider1, provider2));
    }

    @DisplayName("provider manager가 적절한 provider를 찾아 인증하는지 테스트")
    @Test
    void authenticateWithSuitableProvider() {

        // Given
        when(provider1.supports(authentication.getClass())).thenReturn(true);
        when(provider1.authenticate(authentication)).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        // When
        Authentication result = providerManager.authenticate(authentication);

        // Then
        assertThat(result).isEqualTo(authentication);
        verify(provider1).supports(authentication.getClass());
        verify(provider1).authenticate(authentication);

        // provider2는 호출되지 않음
        verify(provider2, never()).authenticate(any());
    }

    @DisplayName("provider가 인증을 지원하지 않는 경우 다음 provider로 넘어가는지 테스트")
    @Test
    void testAuthenticate_WhenProviderSupportsButNotAuthenticated() {
        // given
        when(provider1.supports(authentication.getClass())).thenReturn(true);
        Authentication unauthenticated = mock(Authentication.class);
        when(unauthenticated.isAuthenticated()).thenReturn(false);
        when(provider1.authenticate(authentication)).thenReturn(unauthenticated);

        // provider2는 두 번째 provider
        when(provider2.supports(authentication.getClass())).thenReturn(true);
        when(provider2.authenticate(authentication)).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        // when
        Authentication result = providerManager.authenticate(authentication);

        // then
        // provider1.authenticate() 결과가 인증되지 않으므로 provider2로 넘어간다.
        verify(provider1).authenticate(authentication);
        verify(provider2).authenticate(authentication);
        assertThat(result).isEqualTo(authentication);
    }

    @DisplayName("provider가 인증을 지원하지 않는 경우 NotAuthenticatedException을 던지는지 테스트")
    @Test
    void shouldThrowNotAuthenticatedExceptionWithSuitableProviderNotFound() {

        // given
        when(provider1.supports(authentication.getClass())).thenReturn(false);
        when(provider2.supports(authentication.getClass())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> providerManager.authenticate(authentication))
                .isInstanceOf(NotAuthenticatedException.class)
                .hasMessage("suitable provider not found");

        verify(provider1, never()).authenticate(authentication);
        verify(provider2, never()).authenticate(authentication);
    }
}
