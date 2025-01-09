package springbootmonolithic.domain.member.command.domain.aggregate.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {
    @Id
    @Column(name = "member_code")
    private int memberCode;

    @Id
    @Column(name = "role_code")
    private int roleCode;
}
