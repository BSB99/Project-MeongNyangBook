package com.example.meongnyangbook.report.service;

import com.example.meongnyangbook.report.entity.Report;
import com.example.meongnyangbook.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.meongnyangbook.user.entity.User;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void submitReport(Report report) {
        reportRepository.save(report);
    }
}
