package springbootmonolithic.common.criteria;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchBoardCriteria {

    private String title;                   // 게시글 제목
    private String content;                 // 게시글 내용

    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 페이지 크기
    private int offset;                     // (현재 페이지 번호 - 1) * 페이지 크기
}
