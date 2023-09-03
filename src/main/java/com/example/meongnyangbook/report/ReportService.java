package com.example.meongnyangbook.report;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import java.util.List;

public interface ReportService {

  /**
   * 신고 하기
   *
   * @param user
   * @param userNo
   * @param requestDto
   * @return
   */
  ApiResponseDto submitReport(User user, Long userNo, ReportRequestDto requestDto);

  /**
   * 신고 삭제
   *
   * @param reportNo
   * @return
   */
  ApiResponseDto deleteReport(Long reportNo);

  /**
   * 신고정보 가져오기
   *
   * @return
   */
  List<ReportResponseDto> getReports();
}
