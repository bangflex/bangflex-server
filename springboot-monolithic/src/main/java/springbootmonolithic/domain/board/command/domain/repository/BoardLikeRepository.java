package springbootmonolithic.domain.board.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.BoardLike;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.compositeKey.BoardLikeId;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    boolean existsByBoardCodeAndMemberCode(int boardCode, int memberCode);
}
