package springbootmonolithic.domain.board.query.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("boardControllerQuery")
@RequestMapping("api/v1/board")
public class BoardController {

    @GetMapping
    public String boardHealthCheck() {
        return "board OK";
    }
}
