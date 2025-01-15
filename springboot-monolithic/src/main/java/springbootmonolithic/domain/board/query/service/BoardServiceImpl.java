package springbootmonolithic.domain.board.query.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootmonolithic.common.PageResponse;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.repository.BoardCustomRepository;
import springbootmonolithic.domain.board.query.repository.BoardRepository;

import java.util.List;

@Service("boardServiceQuery")
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardCustomRepository boardCustomRepository;

    @Autowired
    public BoardServiceImpl(ModelMapper modelMapper, BoardCustomRepository boardCustomRepository) {
        this.modelMapper = modelMapper;
        this.boardCustomRepository = boardCustomRepository;
    }

    @Override
    public PageResponse<List<BoardDTO>> getBoardList(String title, String content, int pageNumber, int pageSize) {

        return null;
    }
}
