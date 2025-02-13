package springbootmonolithic.domain.theme.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootmonolithic.domain.theme.command.domain.aggregate.entity.compositeKey.ThemeGenreId;

@Entity
@Table(name = "theme_genre")
@IdClass(ThemeGenreId.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeGenre {
    @Id
    @Column(name = "theme_code")
    private int themeCode;

    @Id
    @Column(name = "genre_code")
    private int genreCode;

    @Column(
            name = "active",
            nullable = false,
            unique = false
    )
    private boolean active;

}
