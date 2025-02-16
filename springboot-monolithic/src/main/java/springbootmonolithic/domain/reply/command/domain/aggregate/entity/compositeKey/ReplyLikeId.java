package springbootmonolithic.domain.reply.command.domain.aggregate.entity.compositeKey;

import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class ReplyLikeId implements Serializable {
    // 댓글별 좋아요 복합키 클래스

    private int replyCode;
    private int memberCode;
}
