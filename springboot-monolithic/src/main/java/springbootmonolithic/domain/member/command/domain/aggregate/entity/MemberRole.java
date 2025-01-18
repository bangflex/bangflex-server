package springbootmonolithic.domain.member.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.compositekey.MemberRolePK;

@Entity
@Table(name = "member_role")
@IdClass(MemberRolePK.class)
@Getter
@Builder
@AllArgsConstructor
public class MemberRole {
    @Id
    @Column(name = "member_code")
    private int memberCode;

    @Id
    @Column(name = "role_code")
    private int roleCode;
}
