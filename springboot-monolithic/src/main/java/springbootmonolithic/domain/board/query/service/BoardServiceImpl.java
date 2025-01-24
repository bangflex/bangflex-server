package springbootmonolithic.domain.board.query.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootmonolithic.common.PageResponse;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.dto.SelectedBoardDTO;
import springbootmonolithic.domain.board.query.repository.BoardCustomRepository;
import springbootmonolithic.exception.BoardNotFoundException;
import springbootmonolithic.exception.InvalidDataException;

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
    public PageResponse<List<BoardDTO>> getBoardList(String word, int pageNumber, int pageSize) {

        int offset = (pageNumber - 1) * pageSize;
        SearchBoardCriteria criteria = new SearchBoardCriteria(word, pageNumber, pageSize, offset);

        List<BoardDTO> boards = boardCustomRepository.findBoardList(criteria);
        int totalCount = boardCustomRepository.getTotalBoardCount(criteria);
        PageResponse<List<BoardDTO>> response = new PageResponse<>(boards, pageNumber, pageSize, totalCount);

        return response;
    }

    @Override
    public SelectedBoardDTO getBoard(int boardCode) {

        // 첨부 이미지 목록 먼저 조회
        List<String> boardFiles = boardCustomRepository.findBoardFilesByBoardCode(boardCode);

        SelectedBoardDTO selectedBoard = boardCustomRepository.findBoardByCode(boardCode);
        if (selectedBoard == null) {
            throw new InvalidDataException("존재하지 않는 게시글입니다.");
        } else if (!selectedBoard.isActive()) {
            throw new BoardNotFoundException("삭제된 게시글입니다.");
        }

        // 조회해온 게시글 DTO에 조회한 이미지 목록 set
        if (boardFiles != null) selectedBoard.setImageFiles(boardFiles);

        return selectedBoard;
    }
}
