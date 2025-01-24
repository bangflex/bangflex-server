package springbootmonolithic.security.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootmonolithic.security.domain.dto.MemberDTO;

@Mapper
public interface AuthMapper {
    MemberDTO selectMemberByEmailWithAuthorities(String email);
}
