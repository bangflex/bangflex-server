package springbootmonolithic.domain.member.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.domain.member.query.service.AuthQueryService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/auth")
public class AuthQueryController {
    private final AuthQueryService authQueryService;

    @Autowired
    public AuthQueryController(AuthQueryService authQueryService) {
        this.authQueryService = authQueryService;
    }


    @GetMapping("/health-check")
    public String healthCheck() {
        return "AuthQuery v1 good";
    }

    @GetMapping("email/check")
    public ResponseEntity<SuccessResponse<Boolean>> emailCheck(@RequestParam(name = "email") String email) {
        authQueryService.validateEmail(email);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        "사용 가능한 이메일입니다.", true, LocalDateTime.now()
                )
        );
    }
}
