package com.example.meongnyangbook.report;

import lombok.Getter;

@Getter
public class ReportResponseDto {

  private Long id;
  private Long repotedUserId; //신고 당한 사람
  private String description;
  private String reportedUser;
  private String user;
  private ReportEnum reportCategory;
  private String createdAt;

  public ReportResponseDto(Report report) {
    this.id = report.getId();
    this.repotedUserId = report.getReportedUser().getId();
    this.description = report.getDescription();
    this.reportedUser = report.getReportedUser().getUsername();
    this.user = report.getUser().getUsername();
    this.reportCategory = report.getReportCategory();
    this.createdAt = report.getCreatedAtAsString();
  }
}
