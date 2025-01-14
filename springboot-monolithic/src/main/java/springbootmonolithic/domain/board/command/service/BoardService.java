package springbootmonolithic.domain.board.command.service;

import org.springframework.web.multipart.MultipartFile;
import springbootmonolithic.domain.board.command.dto.BoardCreateDTO;
import springbootmonolithic.domain.board.command.dto.BoardUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    int createBoard(BoardCreateDTO newBoard, List<MultipartFile> images) throws IOException;

    void updateBoard(int boardCode, BoardUpdateDTO modifiedBoard, List<MultipartFile> images) throws IOException;

    void deleteBoard(int boardCode, int memberCode);
}
