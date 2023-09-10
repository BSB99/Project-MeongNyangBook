package com.example.meongnyangbook.report;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mya/reports")
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

  @Operation(summary = "신고하기")
  @PostMapping("/user/{userNo}")
  public ResponseEntity<ApiResponseDto> submitReport(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ReportRequestDto requestDto, @PathVariable Long userNo) {
    ApiResponseDto result = reportService.submitReport(userDetails.getUser(), userNo, requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "신고 취소")
  @DeleteMapping("{reportNo}")
  public ResponseEntity<ApiResponseDto> deleteReport(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reportNo) {
    ApiResponseDto result = reportService.deleteReport(reportNo);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "신고내역 확인")
  @GetMapping
  public ResponseEntity<List<ReportResponseDto>> getReports(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<ReportResponseDto> result = reportService.getReports();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
