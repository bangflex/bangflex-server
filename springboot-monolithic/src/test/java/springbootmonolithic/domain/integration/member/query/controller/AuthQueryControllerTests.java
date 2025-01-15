package springbootmonolithic.domain.integration.member.query.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
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
    @DisplayName("이메일 검증 요청 - 성공")
    void shouldReturnOkForNonDuplicateEmail() throws Exception {

        String email = "existinguser@example.com";

        mockMvc.perform(
                get("/api/v1/auth/email/validate")
                        .param("email", email)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("사용 가능한 이메일입니다."))
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("이메일 검증 요청 - 중복된 이메일의 경우")
    void shouldReturnConflictForDuplicateEmail() throws Exception {

        String email = "user1@example.com";

        mockMvc.perform(
                        get("/api/v1/auth/email/validate")
                                .param("email", email)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("이미 존재하는 이메일입니다."))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());
    }

    @DisplayName("이메일 검증 요청 - email 파라미터 값 누락된 경우")
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void shouldReturnBadRequestForNullEmail(String blankEmail) throws Exception {
        mockMvc.perform(
                get("/api/v1/auth/email/validate")
                        .param("email", blankEmail)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("이메일은 필수 입력 항목입니다.")))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());
    }
}
