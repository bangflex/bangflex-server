package springbootmonolithic.domain.member.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.domain.member.command.application.dto.request.SignUpRequestDTO;
import springbootmonolithic.domain.member.command.application.service.MemberService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/auth")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<Boolean>> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        memberService.signUp(signUpRequestDTO);
        return ResponseEntity.ok(
                SuccessResponse.<Boolean>builder()
                        .message("회원가입 성공")
                        .result(true)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
