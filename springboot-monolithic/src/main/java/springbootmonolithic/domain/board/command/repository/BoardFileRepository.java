package springbootmonolithic.domain.board.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
}
