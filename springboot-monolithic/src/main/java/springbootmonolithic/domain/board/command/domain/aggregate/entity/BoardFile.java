package springbootmonolithic.domain.board.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_file")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private int code;

    @Column(
            name = "created_at",
            nullable = false,
            unique = false
    )
    private String createdAt;

    @Column(
            name = "url",
            nullable = false,
            unique = false
    )
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
                name = "board_code",
                nullable = false,
                unique = false
    )
    private Board board;
}
