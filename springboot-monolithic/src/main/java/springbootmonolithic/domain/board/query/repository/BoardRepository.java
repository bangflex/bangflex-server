package springbootmonolithic.domain.board.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;

@Repository("boardRepositoryQuery")
public interface BoardRepository extends JpaRepository<Board, Integer>, BoardCustomRepository {
}
