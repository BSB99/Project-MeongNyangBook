package com.example.meongnyangbook.report.repository;

import com.example.meongnyangbook.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // 신고와 관련한 추가적인 조회나 처리가 필요하다면 여기에 메서드를 추가할 수 있습니다.
}
