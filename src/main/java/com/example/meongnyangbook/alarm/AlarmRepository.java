package com.example.meongnyangbook.alarm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  Page<Alarm> findAllByUserId(Long userId, Pageable pageable);
}
