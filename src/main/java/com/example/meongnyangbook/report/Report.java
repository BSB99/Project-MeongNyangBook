package com.example.meongnyangbook.report;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
  @JoinColumn(name = "reported_user_no")
  private User reportedUser;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "report_category", nullable = false)
  private ReportEnum reportCategory; // ReportCategory Enum 사용

  public Report(User reportedUser, User user, ReportRequestDto requestDto) {
    this.description = requestDto.getMsg();
    this.reportCategory = requestDto.getReportEnum();
    this.reportedUser = reportedUser;
    this.user = user;
  }
}
