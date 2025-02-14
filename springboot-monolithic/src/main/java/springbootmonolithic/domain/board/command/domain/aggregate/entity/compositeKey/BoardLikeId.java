package springbootmonolithic.domain.board.command.domain.aggregate.entity.compositeKey;

import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class BoardLikeId implements Serializable {
    // 게시글별 좋아요 복합키 클래스

    private int boardCode;
    private int memberCode;
}
