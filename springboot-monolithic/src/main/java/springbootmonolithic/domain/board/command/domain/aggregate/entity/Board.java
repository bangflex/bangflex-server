package springbootmonolithic.domain.board.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;
import springbootmonolithic.domain.reply.command.domain.aggregate.entity.Reply;

import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Board {

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
            name = "title",
            nullable = false,
            unique = false
    )
    private String title;

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

    // 첨부파일과의 관계 설정
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BoardFile> boardFiles;

    // 댓글과의 관계 설정
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Reply> replies;
}
