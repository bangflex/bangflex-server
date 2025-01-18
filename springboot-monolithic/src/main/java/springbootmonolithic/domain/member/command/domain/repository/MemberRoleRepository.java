package springbootmonolithic.domain.member.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.MemberRole;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.compositekey.MemberRolePK;

public interface MemberRoleRepository extends JpaRepository<MemberRole, MemberRolePK> {
}
