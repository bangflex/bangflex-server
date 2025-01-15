package springbootmonolithic.domain.member.query.service;

public interface AuthQueryService {
    void validateEmail(String email);

    boolean checkEmailDuplicated(String email);
}
