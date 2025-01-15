package springbootmonolithic.domain.member.query.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    boolean isExistsByEmail(String duplicatedEmail);
}
