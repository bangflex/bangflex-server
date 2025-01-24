package springbootmonolithic.common.domain.member.role;


public enum RoleType {
    ADMIN(0, "ROLE_ADMIN"),
    MANAGER(1, "ROLE_MANAGER"),
    USER(2, "ROLE_USER")
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
