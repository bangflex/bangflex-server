package springbootmonolithic.domain.integration.member.query.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthQueryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("이메일 중복 확인 요청 - 중복되지 않은 이메일의 경우")
    void shouldReturnOkForNonDuplicateEmail() throws Exception {

        String email = "existinguser@example.com";

        mockMvc.perform(
                get("/api/v1/auth/email/check")
                        .param("email", email)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("사용 가능한 이메일입니다."))
                .andExpect(jsonPath("$.result").isEmpty())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());
    }

}