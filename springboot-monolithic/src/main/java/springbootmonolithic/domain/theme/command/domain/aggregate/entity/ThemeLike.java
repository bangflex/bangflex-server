package springbootmonolithic.domain.theme.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootmonolithic.domain.theme.command.domain.aggregate.entity.compositeKey.ThemeLikeId;

@Entity
@Table(name = "theme_like")
@IdClass(ThemeLikeId.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeLike {
    @Id
    @Column(name = "theme_code")
    private int themeCode;

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
