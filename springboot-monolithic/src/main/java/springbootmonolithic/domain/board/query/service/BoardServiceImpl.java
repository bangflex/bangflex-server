package springbootmonolithic.domain.board.query.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springbootmonolithic.common.PageResponse;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.repository.BoardCustomRepository;

import java.util.List;

@Service("boardServiceQuery")
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardCustomRepository boardCustomRepository;

    @Autowired
    public BoardServiceImpl(ModelMapper modelMapper,
                            BoardCustomRepository boardCustomRepository) {
        this.modelMapper = modelMapper;
        this.boardCustomRepository = boardCustomRepository;
    }

    @Override
    public PageResponse<List<BoardDTO>> getBoardList(String title, String content, int pageNumber, int pageSize) {

        int offset = (pageNumber - 1) * pageSize;
        SearchBoardCriteria criteria = new SearchBoardCriteria(title, content, pageNumber, pageSize, offset);

        List<BoardDTO> boards = boardCustomRepository.findBoardList(criteria);
        int totalCount = boardCustomRepository.getTotalBoardCount(criteria);
        PageResponse<List<BoardDTO>> response = new PageResponse<>(boards, pageNumber, pageSize, totalCount);

        return response;
    }
}
