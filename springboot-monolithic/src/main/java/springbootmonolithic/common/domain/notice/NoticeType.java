package springbootmonolithic.common.domain.notice;

public enum NoticeType {
    SYSTEM(0, "SYSTEM"),
    EVENT(1, "EVENT")
    ;

    public final int order;
    public final String value;
    NoticeType(
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
