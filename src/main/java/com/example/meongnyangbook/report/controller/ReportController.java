package com.example.meongnyangbook.report.controller;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.report.dto.ReportRequestDto;
import com.example.meongnyangbook.report.dto.ReportResponseDto;
import com.example.meongnyangbook.report.service.ReportService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mya/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/user/{userNo}")
    public ApiResponseDto submitReport(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReportRequestDto requestDto, @PathVariable Long userNo) {
        return reportService.submitReport(userDetails.getUser(), userNo, requestDto);
    }

    @DeleteMapping("{reportNo}")
    public ApiResponseDto deleteReport(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reportNo) {
        return reportService.deleteReport(reportNo);
    }

    @GetMapping
    public List<ReportResponseDto> getReports(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reportService.getReports();
    }
}
