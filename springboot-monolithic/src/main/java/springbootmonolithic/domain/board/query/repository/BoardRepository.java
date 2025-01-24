package springbootmonolithic.domain.board.query.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;
import springbootmonolithic.domain.board.query.dto.BoardDTO;

import java.util.List;

@Primary
public interface BoardRepository extends JpaRepository<Board, Integer>, BoardCustomRepository {

    @Override
    List<BoardDTO> findBoardList(SearchBoardCriteria criteria);

    @Override
    int getTotalBoardCount(SearchBoardCriteria criteria);
}
