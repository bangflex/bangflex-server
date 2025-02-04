package springbootmonolithic.domain.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
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
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            unique = false
    )
    private String password;

    @Column(
            name = "nickname",
            nullable = false,
            unique = true
    )
    private String nickname;

    @Column(
            name = "image",
            nullable = true,
            unique = false
    )
    private String image;

    // 테스트 전용 생성자
    public Member(
            int code,
            String email,
            String password,
            String nickname,
            String image
    ) {
        this.code = code;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }

    private Member(
            int code,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String email,
            String password,
            String nickname,
            String image
    ) {
        this.code = code;
        this.active = active;
        this.createdAt = createdAt.toString();
        this.updatedAt = updatedAt.toString();
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }

    public static Member createMember(
            String email,
            String encodedPassword,
            String nickname,
            String image
    ) {
        return new Member(
                0,
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                email,
                encodedPassword,
                nickname,
                image
        );
    }

    public void deactivate() {
        this.active = false;
    }
}
