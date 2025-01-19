package springbootmonolithic.domain.board.query.repository;

import org.springframework.stereotype.Repository;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.query.dto.BoardDTO;

import java.util.List;

@Repository("boardCustomRepository")
public interface BoardCustomRepository {

    List<BoardDTO> findBoardList(SearchBoardCriteria criteria);

    int getTotalBoardCount(SearchBoardCriteria criteria);
}
