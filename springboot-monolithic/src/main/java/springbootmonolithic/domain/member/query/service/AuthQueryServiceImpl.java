package springbootmonolithic.domain.member.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootmonolithic.domain.member.query.mapper.AuthMapper;
import springbootmonolithic.exception.EmailDuplicatedException;

@Service
public class AuthQueryServiceImpl implements AuthQueryService {
    private final AuthMapper authMapper;

    @Autowired
    public AuthQueryServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public boolean validateEmail(String email) {

        boolean isEmailDuplicated = checkEmailDuplicated(email);

        if (isEmailDuplicated) {
            throw new EmailDuplicatedException("이미 존재하는 이메일입니다.");
        } else {
            return true;
        }
    }

    @Override
    public boolean checkEmailDuplicated(String email) {
        return authMapper.isExistsByEmail(email);
    }
}
