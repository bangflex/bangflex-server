package springbootmonolithic.domain.board.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.compositeKey.BoardLikeId;

@Entity
@Table(name = "board_like")
@IdClass(BoardLikeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLike {

    @Id
    @Column(name = "board_code")
    private int boardCode;

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
