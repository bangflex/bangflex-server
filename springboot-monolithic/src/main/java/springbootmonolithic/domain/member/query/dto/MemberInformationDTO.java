package springbootmonolithic.domain.member.query.dto;

import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInformationDTO {
    private int code;
    private String createdAt;
    private String updatedAt;
    private String email;
    private String nickname;
    private String image;
    private Set<RoleDTO> roleDTO;
}
