package com.example.meongnyangbook.report;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final ReportRepository reportRepository;
  private final UserService userService;

  @Override
  public ApiResponseDto submitReport(User user, Long userNo, ReportRequestDto requestDto) {
    User reportedUser = userService.findUser(userNo);

    if (reportedUser.getUsername().equals(user.getUsername())) {
      throw new IllegalArgumentException("자기 자신은 신고가 안됩니다.");
    }

    Report report = new Report(reportedUser, user, requestDto);

    reportRepository.save(report);

    return new ApiResponseDto("신고가 접수 되었습니다.", 201);
  }

  @Override
  public ApiResponseDto deleteReport(Long reportNo) {
    Report report = reportRepository.findById(reportNo).orElseThrow(() -> {
      throw new IllegalArgumentException("신고된 항목을 찾을 수 없습니다.");
    });

    reportRepository.delete(report);

    return new ApiResponseDto("신고 삭제 완료", 200);
  }

  @Override
  public List<ReportResponseDto> getReports() {
    return reportRepository.findAll().stream().map(ReportResponseDto::new).toList();
  }
}

