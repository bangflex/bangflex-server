package springbootmonolithic.domain.member.query.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.domain.member.query.service.MemberQueryService;

import java.time.LocalDateTime;

@Validated
@RestController
@RequestMapping("api/v1/auth")
public class MemberQueryController {
    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberQueryController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }


    @GetMapping("/health-check")
    public String healthCheck() {
        return "MemberQuery v1 good";
    }

    @GetMapping("/email/validate")
    public ResponseEntity<SuccessResponse<Boolean>> emailCheck(
            @RequestParam(name = "email")
            @NotBlank(message = "이메일은 필수 입력 항목입니다.")
            @Email(message = "유효한 이메일 형식이어야 합니다.") String email
    ) {
        memberQueryService.validateEmail(email);
        return ResponseEntity.ok(
                SuccessResponse.<Boolean>builder()
                        .message("사용 가능한 이메일입니다.")
                        .result(true)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
