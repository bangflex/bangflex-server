package springbootmonolithic.domain.board.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "board")
@Getter
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
            nullable = false,
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

    @Column(
            name = "member_code",
            nullable = false,
            unique = false
    )
    private int memberCode;
}
