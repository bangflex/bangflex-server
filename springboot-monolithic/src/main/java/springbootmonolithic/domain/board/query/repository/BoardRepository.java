package springbootmonolithic.domain.board.query.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;

@Primary
public interface BoardRepository extends JpaRepository<Board, Integer>, BoardCustomRepository {
}
