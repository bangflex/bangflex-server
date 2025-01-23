package springbootmonolithic.domain.board.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SelectedBoardDTO {

    private int boardCode;              // 게시글 코드
    private boolean active;             // 게시글 활성화 여부
    private String createdAt;           // 작성일시
    private String nickname;            // 작성자 닉네임
    private String memberImage;         // 작성자 프로필사진
    private String title;               // 제목
    private String content;             // 내용
    private List<String> imageFiles;    // 첨부사진 목록
    private int replyCount;             // 댓글 수
//    private int likeCount;              // 좋아요 수
//    private boolean liked;              // 게시글 좋아요 여부
//    private boolean bookmarked;         // 게시글 스크랩 여부

    @QueryProjection
    public SelectedBoardDTO(int boardCode,
                            boolean active,
                            String createdAt,
                            String nickname,
                            String memberImage,
                            String title,
                            String content,
                            int replyCount) {
        this.boardCode = boardCode;
        this.active = active;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.memberImage = memberImage;
        this.title = title;
        this.content = content;
        this.replyCount = replyCount;
    }
}
