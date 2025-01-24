package springbootmonolithic.domain.member.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootmonolithic.domain.member.query.mapper.MemberMapper;
import springbootmonolithic.exception.EmailDuplicatedException;
import springbootmonolithic.exception.NicknameDuplicatedException;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberMapper memberMapper;

    @Autowired
    public MemberQueryServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    @Transactional
    public void validateEmail(String email) {
        if (checkEmailDuplicated(email)) {
            throw new EmailDuplicatedException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    @Transactional
    public boolean checkEmailDuplicated(String email) {
        return memberMapper.isExistsByEmail(email);
    }

    @Override
    @Transactional
    public void checkNicknameDuplicated(String nickname) {
        if (memberMapper.isExistsByNickname(nickname)) throw new NicknameDuplicatedException("이미 사용중인 닉네임입니다.");
    }
}
