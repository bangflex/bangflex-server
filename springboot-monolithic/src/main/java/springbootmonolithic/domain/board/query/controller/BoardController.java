package springbootmonolithic.domain.board.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootmonolithic.domain.board.query.service.BoardService;

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
}
