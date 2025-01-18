package springbootmonolithic.domain.member.query.service;

public interface MemberQueryService {
    void checkNicknameDuplicated(String nickname);
}
