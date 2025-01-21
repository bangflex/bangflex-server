package springbootmonolithic.domain.board.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {

    void deleteAllByBoardCode(int boardCode);
}
