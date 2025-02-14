package springbootmonolithic.domain.reply.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;

@Entity
@Table(name = "reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private int code;

    @Column(
            name = "active",
            nullable = false,
            unique = false
    )
    private boolean active;

    @Column(
            name = "created_at",
            nullable = false,
            unique = false
    )
    private String createdAt;

    @Column(
            name = "updated_at",
            nullable = true,
            unique = false
    )
    private String updatedAt;

    @Column(
            name = "content",
            nullable = false,
            unique = false
    )
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
                name = "member_code",
                nullable = false,
                unique = false
    )
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
                name = "board_code",
                nullable = false,
                unique = false
    )
    private Board board;
}
