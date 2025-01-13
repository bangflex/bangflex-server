package springbootmonolithic.domain.board.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springbootmonolithic.common.ResponseMessage;
import springbootmonolithic.domain.board.command.dto.BoardCreateDTO;
import springbootmonolithic.domain.board.command.service.BoardService;

import java.io.IOException;
import java.util.List;

@RestController("boardControllerCommand")
@RequestMapping("api/v1/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "커뮤니티 게시글 등록 API")
    public ResponseEntity<ResponseMessage<Integer>> createBoard(
                    @RequestPart BoardCreateDTO newBoard,
                    @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        int boardCode = boardService.createBoard(newBoard, images);

        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 등록 성공", boardCode));
    }
}
