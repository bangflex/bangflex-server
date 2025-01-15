package springbootmonolithic.domain.member.query.service;

public interface AuthQueryService {
    boolean validateEmail(String email);

    boolean checkEmailDuplicated(String email);
}
