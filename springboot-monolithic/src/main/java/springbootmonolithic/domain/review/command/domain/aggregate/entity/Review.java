package springbootmonolithic.domain.review.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
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
            name = "headcount",
            nullable = false,
            unique = false
    )
    private int headcount;

    @Column(
            name = "passed",
            nullable = false,
            unique = false
    )
    private boolean passed;

    @Column(
            name = "taken_time",
            nullable = false,
            unique = false
    )
    private int takenTime;

    @Column(
            name = "total_score",
            nullable = false,
            unique = false
    )
    private int totalScore;

    @Column(
            name = "level",
            nullable = false,
            unique = false
    )
    private String level;

    @Column(
            name = "horror_level",
            nullable = false,
            unique = false
    )
    private String horrorLevel;

    @Column(
            name = "activity",
            nullable = false,
            unique = false
    )
    private String activity;

    @Column(
            name = "interior",
            nullable = false,
            unique = false
    )
    private String interior;

    @Column(
            name = "probability",
            nullable = false,
            unique = false
    )
    private String probability;

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

    @Column(
            name = "theme_code",
            nullable = false,
            unique = false
    )
    private int themeCode;
}
