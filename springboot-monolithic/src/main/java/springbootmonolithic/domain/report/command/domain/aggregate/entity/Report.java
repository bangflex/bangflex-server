package springbootmonolithic.domain.report.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbootmonolithic.common.domain.report.ReportType;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private int code;

    @Column(
            name = "active",
            nullable = false,
            unique = false
    )
    private boolean active;

    @Column(
            name = "created_at",
            nullable = false,
            unique = false
    )
    private String createdAt;

    @Column(
            name = "target_code",
            nullable = false,
            unique = false
    )
    private int targetCode;

    @Column(
            name = "type",
            nullable = false,
            unique = false
    )
    private ReportType type;

    @Column(
            name = "reason",
            nullable = false,
            unique = false
    )
    private String reason;

    @Column(
            name = "detail",
            nullable = false,
            unique = false
    )
    private String detail;

    @Column(
            name = "reporter",
            nullable = false,
            unique = false
    )
    private int reporter;
}
