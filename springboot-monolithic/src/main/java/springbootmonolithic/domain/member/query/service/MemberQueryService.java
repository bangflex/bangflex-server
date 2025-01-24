package springbootmonolithic.domain.member.query.service;

public interface MemberQueryService {
    void validateEmail(String email);
    boolean checkEmailDuplicated(String email);
    void checkNicknameDuplicated(String nickname);
}
