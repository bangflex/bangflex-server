package springbootmonolithic.common.domain.report;

public enum ReportType {
    MEMBER(0, "MEMBER"),
    BOARD(1, "BOARD"),
    REVIEW(2, "REVIEW"),
    COMMENT(3, "COMMENT")
    ;

    private final int order;
    private final String value;

    ReportType(
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
