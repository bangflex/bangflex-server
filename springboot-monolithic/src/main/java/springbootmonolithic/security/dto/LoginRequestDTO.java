package springbootmonolithic.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDTO {
    private String loginId;
    private String password;
}
