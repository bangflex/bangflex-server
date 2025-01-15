package springbootmonolithic.domain.board.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
