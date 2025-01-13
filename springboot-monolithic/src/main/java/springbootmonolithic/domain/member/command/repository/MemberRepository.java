package springbootmonolithic.domain.member.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
