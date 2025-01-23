package springbootmonolithic.domain.board.query.repository;

import org.springframework.stereotype.Repository;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.dto.SelectedBoardDTO;

import java.util.List;

@Repository("boardCustomRepository")
public interface BoardCustomRepository {

    List<BoardDTO> findBoardList(SearchBoardCriteria criteria);

    int getTotalBoardCount(SearchBoardCriteria criteria);

    List<String> findBoardFilesByBoardCode(int boardCode);

    SelectedBoardDTO findBoardByCode(int boardCode);
}
