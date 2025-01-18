package springbootmonolithic.domain.member.command.application.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String nickname;
    private String image;
}
