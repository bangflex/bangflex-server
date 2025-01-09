package springbootmonolithic.domain.theme.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theme")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
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
            name = "name",
            nullable = false,
            unique = false
    )
    private String name;

    @Column(
            name = "level",
            nullable = true,
            unique = false
    )
    private String level;

    @Column(
            name = "time_limit",
            nullable = true,
            unique = false
    )
    private String timeLimit;

    @Column(
            name = "story",
            nullable = true,
            unique = false
    )
    private String story;

    @Column(
            name = "price",
            nullable = true,
            unique = false
    )
    private String price;

    @Column(
            name = "headcount",
            nullable = true,
            unique = false
    )
    private String headcount;

    @Column(
            name = "poster_image",
            nullable = true,
            unique = false
    )
    private String posterImage;

    @Column(
            name = "store_code",
            nullable = false,
            unique = false
    )
    private int storeCode;
}
