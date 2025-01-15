package springbootmonolithic.domain.member.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootmonolithic.domain.member.query.mapper.AuthMapper;

@Service
public class AuthQueryServiceImpl implements AuthQueryService {
    private final AuthMapper authMapper;

    @Autowired
    public AuthQueryServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public boolean isEmailDuplicated(String email) {
        return authMapper.isExistsByEmail(email);
    }
}
