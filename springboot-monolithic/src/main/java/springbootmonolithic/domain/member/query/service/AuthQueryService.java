package springbootmonolithic.domain.member.query.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthQueryService extends UserDetailsService {
    void validateEmail(String email);
    boolean checkEmailDuplicated(String email);
    @Override
    UserDetails loadUserByUsername(String email);
}
