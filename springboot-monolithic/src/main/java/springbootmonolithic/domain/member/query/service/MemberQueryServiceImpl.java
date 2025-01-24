package springbootmonolithic.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootmonolithic.domain.member.query.dto.MemberInformationDTO;
import springbootmonolithic.domain.member.query.mapper.MemberMapper;
import springbootmonolithic.exception.EmailDuplicatedException;
import springbootmonolithic.exception.MemberNotFoundException;
import springbootmonolithic.exception.NicknameDuplicatedException;

@Slf4j
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

    /**
     * 이메일로 회원 정보 조회
     *
     * @param email 이메일
     * @return 회원 정보
     */
    @Override
    @Transactional
    public MemberInformationDTO getMemberInformationByEmail(String email) {
        MemberInformationDTO memberInformationDTO = memberMapper.selectMemberInformationByEmail(email);
        if (memberInformationDTO == null) {
            throw new MemberNotFoundException("해당 이메일로 가입된 회원이 없습니다.");
        }
        return memberInformationDTO;
    }
}
