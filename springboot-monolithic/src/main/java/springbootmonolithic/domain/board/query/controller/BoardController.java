package springbootmonolithic.domain.board.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springbootmonolithic.common.PageResponse;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.service.BoardService;

import java.time.LocalDateTime;
import java.util.List;

@RestController("boardControllerQuery")
@RequestMapping("api/v1/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/check")
    @Operation(summary = "Board Controller 체크용 API")
    public String boardHealthCheck() {
        return "board OK";
    }

    @GetMapping("")
    @Operation(summary = "커뮤니티 게시글 목록 조회 API - 페이지네이션")
    public ResponseEntity<SuccessResponse<PageResponse<List<BoardDTO>>>> getBoardList(
                                            @RequestParam(required = false) String word,
                                            @RequestParam (value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<BoardDTO>> boardList = boardService.getBoardList(word, pageNumber, pageSize);

        return ResponseEntity.ok(new SuccessResponse<>("게시글 목록 조회 성공", boardList, LocalDateTime.now()));
    }
}
