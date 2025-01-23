package springbootmonolithic.domain.board.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootmonolithic.common.PageResponse;
import springbootmonolithic.common.response.SuccessResponse;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.dto.SelectedBoardDTO;
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
    @Operation(summary = "커뮤니티 게시글 목록 조회 API",
                description = "페이지네이션 - 페이지는 1부터 시작" +
                                " / word에 검색어 넣으면 해당 검색어가 포함된 제목이나 내용의 게시글 목록만 반환")
    public ResponseEntity<SuccessResponse<PageResponse<List<BoardDTO>>>> getBoardList(
                                            @RequestParam(required = false) String word,
                                            @RequestParam (value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageResponse<List<BoardDTO>> boardList = boardService.getBoardList(word, pageNumber, pageSize);

        return ResponseEntity.ok(new SuccessResponse<>("게시글 목록 조회 성공", boardList, LocalDateTime.now()));
    }

    @GetMapping("/{boardCode}")
    @Operation(summary = "커뮤니티 게시글 상세 조회 API",
                description = "게시글 코드로 해당 게시글 1개 상세 조회")
    public ResponseEntity<SuccessResponse<SelectedBoardDTO>> getBoard(@PathVariable int boardCode) {

        SelectedBoardDTO board = boardService.getBoard(boardCode);

        return ResponseEntity.ok(new SuccessResponse<>(
                                boardCode + "번 게시글 상세 조회 성공", board, LocalDateTime.now()));
    }
}
