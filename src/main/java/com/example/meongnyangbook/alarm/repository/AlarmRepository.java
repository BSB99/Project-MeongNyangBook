package com.example.meongnyangbook.alarm.repository;

import com.example.meongnyangbook.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUserId(Long userId);
}
