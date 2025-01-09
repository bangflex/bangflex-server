package springbootmonolithic.common.domain.report;

public enum ReportType {
    MEMBER(0, "MEMBER"),
    BOARD(1, "BOARD"),
    REVIEW(2, "REVIEW"),
    COMMENT(3, "COMMENT")
    ;

    private int order;
    private String value;

    ReportType(
            int order,
            String value
    ) {
        order = this.order;
        value = this.value;
    }

    public int getOrder() {
        return order;
    }

    public String getValue() {
        return value;
    }
}
