package springbootmonolithic.security.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;

@Getter
@ToString
@NoArgsConstructor
public class MemberDTO {
    private int code;
    private String email;
    private String password;
    private String nickname;
    private HashSet<RoleDTO> roleDTO;
}
