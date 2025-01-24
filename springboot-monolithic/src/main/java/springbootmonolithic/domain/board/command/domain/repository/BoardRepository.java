package springbootmonolithic.domain.board.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;

@Repository("boardRepositoryCommand")
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
