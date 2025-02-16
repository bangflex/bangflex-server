package springbootmonolithic.domain.reply.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootmonolithic.domain.reply.command.domain.aggregate.entity.compositeKey.ReplyLikeId;

@Entity
@Table(name = "reply_like")
@IdClass(ReplyLikeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyLike {

    @Id
    @Column(name = "reply_code")
    private int replyCode;

    @Id
    @Column(name = "member_code")
    private int memberCode;

    @Column(
            name = "created_at",
            nullable = false,
            unique = false
    )
    private String createdAt;
}
