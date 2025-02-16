package springbootmonolithic.domain.review.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootmonolithic.domain.review.command.domain.aggregate.entity.compositeKey.ReviewLikeId;

@Entity
@Table(name = "review_like")
@IdClass(ReviewLikeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewLike {

    @Id
    @Column(name = "review_code")
    private int reviewCode;

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
