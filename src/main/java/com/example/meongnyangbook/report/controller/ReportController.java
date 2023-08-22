package com.example.meongnyangbook.report.controller;

import com.example.meongnyangbook.report.entity.Report;
import com.example.meongnyangbook.report.service.ReportService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.example.meongnyangbook.user.entity.User;

@RestController
@RequestMapping("/mya/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ReportController(ReportService reportService, UserDetailsService userDetailsService) {
        this.reportService = reportService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/report")
    public ResponseEntity<String> submitReport(@RequestBody Report report) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        report.setReporter(user);
        reportService.submitReport(report);

        return ResponseEntity.ok("신고 되었습니다.");
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ((UserDetailsImpl) userDetails).getUser();
        }
        return null;
    }
}
