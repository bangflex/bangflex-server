package springbootmonolithic.domain.review.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_file")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFile {
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

    @Column(
            name = "review_code",
            nullable = false,
            unique = false
    )
    private int reviewCode;
}
