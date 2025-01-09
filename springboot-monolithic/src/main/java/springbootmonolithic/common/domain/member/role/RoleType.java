package springbootmonolithic.common.domain.member.role;


public enum RoleType {
    ADMIN(0, "ADMIN"),
    MANAGER(1, "MANAGER"),
    USER(2, "USER")
    ;

    private final int order;
    private final String value;

    RoleType(
            int order,
            String value
    ) {
        this.order = order;
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public String getValue() {
        return value;
    }
}
