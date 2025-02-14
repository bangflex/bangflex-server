package springbootmonolithic.domain.member.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByCodeAndActiveTrue(int memberCode);
}
