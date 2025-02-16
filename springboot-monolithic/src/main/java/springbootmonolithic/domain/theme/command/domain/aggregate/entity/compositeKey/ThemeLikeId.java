package springbootmonolithic.domain.theme.command.domain.aggregate.entity.compositeKey;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class ThemeLikeId implements Serializable {
    // 테마별 좋아요 복합키 클래스

    private int themeCode;
    private int memberCode;
}
