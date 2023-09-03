package com.example.meongnyangbook.alarm;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  List<Alarm> findByUserId(Long userId);
}
