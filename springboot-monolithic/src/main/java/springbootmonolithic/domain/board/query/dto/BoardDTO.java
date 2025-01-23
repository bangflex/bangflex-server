package springbootmonolithic.domain.board.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {

    private int boardCode;              // 게시글 코드
    private String createdAt;           // 작성일시
    private String nickname;            // 작성자 닉네임
    private String memberImage;         // 작성자 프로필사진
    private String title;               // 제목
    private String content;             // 내용
    private String imageFile;           // 첫번째 첨부사진 1장 url
    private int replyCount;             // 댓글 수
//    private int likeCount;              // 좋아요 수

    @QueryProjection
    public BoardDTO(int boardCode,
                    String createdAt,
                    String nickname,
                    String memberImage,
                    String title,
                    String content,
                    String imageFile,
                    int replyCount) {
        this.boardCode = boardCode;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.memberImage = memberImage;
        this.title = title;
        this.content = content;
        this.imageFile = imageFile;
        this.replyCount = replyCount;
    }
}
