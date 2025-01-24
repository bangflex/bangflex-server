package springbootmonolithic.security.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {
    private int code;
    private String email;
    private String password;
    private String nickname;
    private Set<RoleDTO> roleDTO;
}
