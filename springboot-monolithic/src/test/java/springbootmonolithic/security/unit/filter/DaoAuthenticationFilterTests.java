package springbootmonolithic.security.unit.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import springbootmonolithic.security.dto.LoginRequestDTO;
import springbootmonolithic.security.filter.DaoAuthenticationFilter;
import springbootmonolithic.security.provider.ProviderManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoAuthenticationFilterTests {

    @Mock
    private ProviderManager providerManager;

    @Mock
    private ObjectMapper objectMapper;

    private DaoAuthenticationFilter daoAuthenticationFilter;

    @BeforeEach
    void setUp() {
        daoAuthenticationFilter = new DaoAuthenticationFilter(providerManager, objectMapper);
    }

    @DisplayName("로그인 - 성공")
    @Test
    void loginSuccess() throws Exception {

        // Given
        LoginRequestDTO loginRequest = new LoginRequestDTO("signed up user", "correct password");
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(mockRequest.getInputStream()).thenReturn(
                new DelegatingServletInputStream(new ByteArrayInputStream(
                        new ObjectMapper().writeValueAsBytes(loginRequest)
                ))
        );
        when(objectMapper.readValue(any(InputStream.class), eq(LoginRequestDTO.class)))
                .thenReturn(loginRequest);
        when(providerManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("signed up user", "correct password"));

        // When
        Authentication result = daoAuthenticationFilter.attemptAuthentication(mockRequest, mockResponse);

        // then
        assertNotNull(result);
        assertEquals("signed up user", result.getName());
        assertEquals("correct password", result.getCredentials());
        verify(providerManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
