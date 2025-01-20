package springbootmonolithic.domain.member.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootmonolithic.domain.member.query.dto.MemberDTO;

@Mapper
public interface AuthMapper {
    boolean isExistsByEmail(String duplicatedEmail);
    MemberDTO selectMemberByEmailWithAuthorities(String email);
}
