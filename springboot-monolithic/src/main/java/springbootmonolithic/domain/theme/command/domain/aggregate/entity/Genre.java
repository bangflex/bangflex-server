package springbootmonolithic.domain.theme.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genre")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
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
            name = "name",
            nullable = false,
            unique = true
    )
    private String name;
}
