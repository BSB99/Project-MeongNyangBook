package com.example.meongnyangbook.report.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_reports")
public class Report extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_category", nullable = false)
    private ReportCategory reportCategory; // ReportCategory Enum 사용

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum ReportCategory {
        INAPPROPRIATE_BEHAVIOR,
        FALSE_POSTING,
        OTHER
    }
}
