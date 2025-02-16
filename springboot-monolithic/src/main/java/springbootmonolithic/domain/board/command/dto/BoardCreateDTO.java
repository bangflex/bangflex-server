package springbootmonolithic.domain.board.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardCreateDTO {

    private String email;           // 작성자 이메일(아이디)
    private String title;           // 제목
    private String content;         // 내용
}
