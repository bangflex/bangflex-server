package springbootmonolithic.security.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootmonolithic.security.domain.dto.MemberDTO;
import springbootmonolithic.security.domain.dto.RoleDTO;
import springbootmonolithic.security.domain.mapper.AuthMapper;
import springbootmonolithic.security.domain.CustomUser;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class AuthQueryServiceImpl implements AuthQueryService {
    private final AuthMapper authMapper;

    @Autowired
    public AuthQueryServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        MemberDTO loginMember = authMapper.selectMemberByEmailWithAuthorities(email);

        if (loginMember == null) {
            log.error("사용자를 찾을 수 없습니다.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleDTO roleDTO : loginMember.getRoleDTO()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleDTO.getRole()));
        }

        return new CustomUser(
                loginMember.getCode(),
                loginMember.getEmail(),
                loginMember.getPassword(),
                loginMember.getNickname(),
                grantedAuthorities
        );
    }
}
