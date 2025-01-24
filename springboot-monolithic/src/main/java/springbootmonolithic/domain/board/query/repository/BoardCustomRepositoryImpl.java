package springbootmonolithic.domain.board.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import springbootmonolithic.common.criteria.SearchBoardCriteria;
import springbootmonolithic.domain.board.command.domain.aggregate.entity.Board;
import springbootmonolithic.domain.board.query.dto.BoardDTO;
import springbootmonolithic.domain.board.query.dto.SelectedBoardDTO;

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
                .select(Projections.constructor(BoardDTO.class,
                        board.code.as("boardCode"),
                        board.createdAt,
                        member.nickname,
                        member.image.as("memberImage"),
                        board.title,
                        board.content,
                        boardFile.url.as("imageFile"),
                        reply.code.count().intValue().as("replyCount")))
                .from(board)
                .leftJoin(board.member, member)
                .leftJoin(board.boardFiles, boardFile)
                .leftJoin(board.replies, reply)
                .where(board.active.eq(true)
                        .and(reply.active.eq(true).or(reply.isNull()))
                        .and(resultLikes(criteria.getWord())))
                .groupBy(board.code)
                .orderBy(board.createdAt.desc())
                .fetch();

        return boardList;
    }

    private BooleanExpression resultLikes(String word) {
        return word != null ? (board.title.like("%" + word + "%")
                                .or(board.content.like("%" + word + "%"))) : null;
    }

    @Override
    public int getTotalBoardCount(SearchBoardCriteria criteria) {

        Long totalCountLong = queryFactory
                .select(board.code.count())
                .from(board)
                .where(board.active.eq(true)
                        .and(resultLikes(criteria.getWord())))
                .distinct()
                .fetchOne();

        int totalCount = (totalCountLong != null) ? totalCountLong.intValue() : 0;

        return totalCount;
    }

    @Override
    public List<String> findBoardFilesByBoardCode(int boardCode) {

        List<String> fileUrls = queryFactory
                .select(boardFile.url)
                .from(boardFile)
                .where(boardFile.board.code.eq(boardCode))
                .orderBy(boardFile.code.asc())
                .fetch();

        return !fileUrls.isEmpty() ? fileUrls : null;
    }

    @Override
    public SelectedBoardDTO findBoardByCode(int boardCode) {

        SelectedBoardDTO foundBoard = queryFactory
                .select(Projections.constructor(SelectedBoardDTO.class,
                        board.code.as("boardCode"),
                        board.active,
                        board.createdAt,
                        member.nickname,
                        member.image.as("memberImage"),
                        board.title,
                        board.content,
                        reply.code.count().intValue().as("replyCount")))
                .from(board)
                .join(board.member, member)
                .leftJoin(board.replies, reply)
                .where(board.code.eq(boardCode)
                        .and(reply.active.eq(true).or(reply.isNull())))
                .fetchOne();

        return foundBoard;
    }
}
