package springbootmonolithic.domain.member.command.application.service;

import springbootmonolithic.domain.member.command.application.dto.request.SignUpRequestDTO;

public interface AuthService {
    void signUp(SignUpRequestDTO signupRequestDTO);
}
