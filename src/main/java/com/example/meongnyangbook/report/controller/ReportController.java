package com.example.meongnyangbook.report.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.report.dto.ReportRequestDto;
import com.example.meongnyangbook.report.dto.ReportResponseDto;
import com.example.meongnyangbook.report.service.ReportService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/user/{userNo}")
    public ResponseEntity<ApiResponseDto> submitReport(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReportRequestDto requestDto, @PathVariable Long userNo) {
        ApiResponseDto result = reportService.submitReport(userDetails.getUser(), userNo, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("{reportNo}")
    public ResponseEntity<ApiResponseDto> deleteReport(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reportNo) {
        ApiResponseDto result = reportService.deleteReport(reportNo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getReports(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ReportResponseDto> result = reportService.getReports();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
