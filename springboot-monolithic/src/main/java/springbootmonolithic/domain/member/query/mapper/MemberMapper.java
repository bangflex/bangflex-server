package springbootmonolithic.domain.member.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootmonolithic.domain.member.query.dto.MemberInformationDTO;

@Mapper
public interface MemberMapper {
    boolean isExistsByNickname(String nickname);
    boolean isExistsByEmail(String email);
    MemberInformationDTO selectMemberInformationByEmail(String email);
}
