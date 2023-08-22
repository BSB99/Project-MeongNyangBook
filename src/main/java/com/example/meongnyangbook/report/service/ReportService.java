package com.example.meongnyangbook.report.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.report.dto.ReportRequestDto;
import com.example.meongnyangbook.report.dto.ReportResponseDto;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface ReportService {
    ApiResponseDto submitReport(User user, Long userNo, ReportRequestDto requestDto);

    ApiResponseDto deleteReport(Long reportNo);

    List<ReportResponseDto> getReports();
}
