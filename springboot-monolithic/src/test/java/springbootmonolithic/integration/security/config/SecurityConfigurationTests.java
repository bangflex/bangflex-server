package springbootmonolithic.integration.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import springbootmonolithic.security.dto.LoginRequestDTO;
import springbootmonolithic.security.exception.AuthenticationEntryPointImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class SecurityConfigurationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity()) // Spring Security 적용
                .build();
        System.out.println("mockMvc = " + mockMvc);
    }
    
    @DisplayName("로그인 - 성공")
    @Test
    void loginSuccess() throws Exception {
        mockMvc.perform(
                post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequestDTO("test@gmail.com", "1234")))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Authorization"))
                .andExpect(header().exists("Refresh-Token"))
                .andExpect(jsonPath("$.message").value("로그인 성공"))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());
    }

    @DisplayName("로그인 - 실패")
    @Test
    void loginFailed() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new LoginRequestDTO("wrong@Email", "wrongPassword")))
                )
                .andDo(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    System.out.println("Status: " + response.getStatus());
                    System.out.println("Content-Type: " + response.getContentType());
                    System.out.println("Body: " + response.getContentAsString());
                    System.out.println("Raw Content-Type: " + result.getResponse().getContentType());

                    // Call AuthenticationEntryPointImpl to handle the error
                    AuthenticationException exception = new BadCredentialsException("Invalid credentials");
                    AuthenticationEntryPointImpl entryPoint = new AuthenticationEntryPointImpl(objectMapper);
                    entryPoint.commence(result.getRequest(), response, exception);
                })
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().doesNotExist("Authorization"))
                .andExpect(header().doesNotExist("Refresh-Token"))

                .andExpect(jsonPath("$.message").value("인증이 필요합니다."))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());
    }
}
