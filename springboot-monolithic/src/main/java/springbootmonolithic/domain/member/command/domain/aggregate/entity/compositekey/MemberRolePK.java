package springbootmonolithic.domain.member.command.domain.aggregate.entity.compositekey;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class MemberRolePK implements Serializable {
    private int memberCode;
    private int roleCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberRolePK that = (MemberRolePK) o;
        return memberCode == that.memberCode && roleCode == that.roleCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, roleCode);
    }
}
