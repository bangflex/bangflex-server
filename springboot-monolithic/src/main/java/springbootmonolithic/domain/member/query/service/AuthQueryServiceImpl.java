package springbootmonolithic.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootmonolithic.domain.member.query.dto.MemberDTO;
import springbootmonolithic.domain.member.query.dto.RoleDTO;
import springbootmonolithic.domain.member.query.mapper.AuthMapper;
import springbootmonolithic.exception.EmailDuplicatedException;
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

    @Transactional
    @Override
    public void validateEmail(String email) {
        if (checkEmailDuplicated(email)) {
            throw new EmailDuplicatedException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    public boolean checkEmailDuplicated(String email) {
        return authMapper.isExistsByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
