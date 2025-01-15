package springbootmonolithic.domain.member.query.service;

public interface AuthQueryService {
    boolean isEmailDuplicated(String email);
}
