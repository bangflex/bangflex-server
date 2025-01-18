package springbootmonolithic.domain.member.query.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    boolean isExistsByNickname(String nickname);
}
