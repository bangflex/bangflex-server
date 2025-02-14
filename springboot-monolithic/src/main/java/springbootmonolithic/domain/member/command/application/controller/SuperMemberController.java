package springbootmonolithic.domain.member.command.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.domain.member.command.application.service.MemberService;

import java.time.LocalDateTime;

@RequestMapping("/api/v1/super/auth")
public class SuperMemberController {
    private final MemberService memberService;

    public SuperMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PatchMapping("/deactivate/{memberCode}")
    public ResponseEntity<SuccessResponse<Boolean>> userDeactivate(@PathVariable int memberCode) {
        memberService.deactivateMemberBy(memberCode);
        return ResponseEntity.ok(
                SuccessResponse.<Boolean>builder()
                        .message(memberCode + "번 유저가 비활성화 됩니다.")
                        .result(true)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
