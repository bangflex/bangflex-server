package springbootmonolithic.domain.board.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardUpdateDTO {

    private int memberCode;         // 작성자
    private String title;           // 제목
    private String content;         // 내용
}
