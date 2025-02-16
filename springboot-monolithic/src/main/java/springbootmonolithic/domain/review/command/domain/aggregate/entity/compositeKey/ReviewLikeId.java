package springbootmonolithic.domain.review.command.domain.aggregate.entity.compositeKey;

import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class ReviewLikeId implements Serializable {
    // 리뷰별 좋아요 복합키 클래스

    private int reviewCode;
    private int memberCode;
}
