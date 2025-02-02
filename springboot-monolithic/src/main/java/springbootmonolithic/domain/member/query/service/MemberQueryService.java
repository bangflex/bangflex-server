package springbootmonolithic.domain.member.query.service;

import springbootmonolithic.domain.member.query.dto.MemberInformationDTO;

public interface MemberQueryService {
    void validateEmail(String email);
    boolean checkEmailDuplicated(String email);
    void checkNicknameDuplicated(String nickname);
    MemberInformationDTO getMemberInformationByEmail(String email);
}
