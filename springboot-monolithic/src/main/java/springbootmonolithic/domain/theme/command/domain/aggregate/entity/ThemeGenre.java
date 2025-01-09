package springbootmonolithic.domain.theme.command.domain.aggregate.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theme_genre")
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
