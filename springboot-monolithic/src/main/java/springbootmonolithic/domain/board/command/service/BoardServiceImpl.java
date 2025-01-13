package springbootmonolithic.domain.board.command.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.BoardFile;
import springbootmonolithic.domain.board.command.dto.BoardCreateDTO;
import springbootmonolithic.domain.board.command.repository.BoardFileRepository;
import springbootmonolithic.domain.board.command.repository.BoardRepository;
import springbootmonolithic.domain.member.command.domain.aggregate.entity.Member;
import springbootmonolithic.domain.member.command.repository.MemberRepository;
import springbootmonolithic.exception.InvalidDataException;
import springbootmonolithic.exception.MemberNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BoardServiceImpl(ModelMapper modelMapper,
                            BoardRepository boardRepository,
                            BoardFileRepository boardFileRepository,
                            MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
        this.boardFileRepository = boardFileRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public int createBoard(BoardCreateDTO newBoard, List<MultipartFile> images) throws IOException {

        // 작성자가 회원이 아니라면 오류 발생
        Member author = memberRepository.findById(newBoard.getMemberCode())
                        .orElseThrow(() -> new MemberNotFoundException("서비스의 회원이 아닙니다."));

        // 제목이나 내용이 null이면 오류 발생
        if (newBoard.getTitle() == null) {
            throw new InvalidDataException("제목을 입력해 주세요.");
        } else if (newBoard.getContent() == null) {
            throw new InvalidDataException("내용을 입력해 주세요.");
        }

        // 게시글 저장
        Board board = Board.builder()
                        .active(true)
                        .createdAt(String.valueOf(LocalDateTime.now()))
                        .title(newBoard.getTitle())
                        .content(newBoard.getContent())
                        .member(author)
                        .build();

        Board savedBoard = boardRepository.save(board);

        // 첨부된 이미지 파일이 있으면 저장
        if (images != null) {
            List<BoardFile> addedImages = saveFiles(images, savedBoard);

            boardRepository.save(savedBoard.toBuilder()
                                    .boardFiles(addedImages)
                                    .build()
            );
        }

        // 저장된 게시글 코드 반환
        return savedBoard.getCode();
    }

    private List<BoardFile> saveFiles(List<MultipartFile> images, Board savedBoard) throws IOException {

        List<BoardFile> boardFiles = new ArrayList<>();

        for (MultipartFile file : images) {
            String fileName = file.getOriginalFilename();

            // 파일이름만 남김
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            // UUID 생성
            String uuid = UUID.randomUUID().toString();
            // 저장 경로
            String filePath = "src/main/resources/static/uploadFiles/boardFiles/" + uuid + fileName;
            Path path = Paths.get(filePath);
            // DB 저장명
            String dbUrl = "/uploadFiles/boardFiles/" + uuid + fileName;

            //저장
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            BoardFile addedImages = boardFileRepository.save(BoardFile.builder()
                                    .createdAt(String.valueOf(LocalDateTime.now()))
                                    .url(dbUrl)
                                    .board(savedBoard)
                                    .build()
            );

            boardFiles.add(addedImages);
        }

        return boardFiles;
    }
}
