package springbootmonolithic.domain.board.query.service;

import springbootmonolithic.common.PageResponse;
import springbootmonolithic.domain.board.query.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    PageResponse<List<BoardDTO>> getBoardList(String title, String content, int pageNumber, int pageSize);
}
