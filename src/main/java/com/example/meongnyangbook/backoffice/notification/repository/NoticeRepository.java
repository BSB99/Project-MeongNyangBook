package com.example.meongnyangbook.backoffice.notification.repository;

import com.example.meongnyangbook.backoffice.notification.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
