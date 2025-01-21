package springbootmonolithic.security.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthQueryService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String email);
}
