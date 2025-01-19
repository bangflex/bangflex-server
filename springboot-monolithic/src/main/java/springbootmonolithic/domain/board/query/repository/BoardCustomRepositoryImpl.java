package springbootmonolithic.domain.board.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.query.dto.BoardDTO;

import java.util.List;

import static springbootmonolithic.domain.board.command.domain.aggregate.entity.QBoard.board;
import static springbootmonolithic.domain.board.command.domain.aggregate.entity.QBoardFile.boardFile;
import static springbootmonolithic.domain.member.command.domain.aggregate.entity.QMember.member;
import static springbootmonolithic.domain.reply.command.domain.aggregate.entity.QReply.reply;

public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BoardDTO> findBoardList(SearchBoardCriteria criteria) {

        List<BoardDTO> boardList = queryFactory
                .select(Projections.fields(BoardDTO.class,
                        board.code.as("boardCode"),
                        board.createdAt,
                        member.nickname,
                        member.image.as("memberImage"),
                        board.title,
                        board.content,
                        boardFile.url.as("imageFile"),
                        reply.code.count().intValue().as("replyCount")))
                .from(board)
                .join(board.member, member).fetchJoin()
                .join(boardFile.board, board).orderBy(boardFile.code.asc()).limit(1)
                .join(reply.board, board).fetchJoin()
                .where(board.active.eq(true))
                .where(titleLikes(criteria.getTitle()), contentLikes(criteria.getContent()))
                .orderBy(board.createdAt.desc())
                .fetch();

        return boardList;
    }

    private BooleanExpression titleLikes(String title) {
        return title != null ? board.title.like("%" + title + "%") : null;
    }

    private BooleanExpression contentLikes(String content) {
        return content != null ? board.content.like("%" + content + "%") : null;
    }

    @Override
    public int getTotalBoardCount(SearchBoardCriteria criteria) {

        Long totalCountLong = queryFactory
                .select(board.code.count())
                .from(board)
                .where(board.active.eq(true))
                .where(titleLikes(criteria.getTitle()), contentLikes(criteria.getContent()))
                .fetchOne();

        int totalCount = (totalCountLong != null) ? totalCountLong.intValue() : 0;

        return totalCount;
    }
}
