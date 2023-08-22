package com.example.meongnyangbook.report.dto;

import com.example.meongnyangbook.report.entity.Report;
import com.example.meongnyangbook.report.entity.ReportEnum;
import lombok.Getter;

@Getter
public class ReportResponseDto {
    private Long id;
    private String description;
    private String reportedUser;
    private String user;
    private ReportEnum reportCategory;
    private String createdAt;

    public ReportResponseDto (Report report) {
        this.id = report.getId();
        this.description = report.getDescription();
        this.reportedUser = report.getReportedUser().getUsername();
        this.user = report.getUser().getUsername();
        this.reportCategory = report.getReportCategory();
        this.createdAt = report.getCreatedAtAsString();
    }
}
