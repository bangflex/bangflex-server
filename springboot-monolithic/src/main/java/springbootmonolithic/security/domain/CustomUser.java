package springbootmonolithic.security.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@ToString
public class CustomUser implements UserDetails {
    private final int code;
    private final String username;  // id
    private final String password;
    private final String nickname;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUser(
            int code,
            String username,
            String password,
            String nickname,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super();
        this.code = code;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
    }
}
