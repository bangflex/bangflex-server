package springbootmonolithic.domain.member.command.application.service;

import springbootmonolithic.domain.member.command.application.dto.request.SignUpRequestDTO;

public interface MemberService {
    void signUp(SignUpRequestDTO signupRequestDTO);
    void deactivateMemberBy(int memberCode);
}
