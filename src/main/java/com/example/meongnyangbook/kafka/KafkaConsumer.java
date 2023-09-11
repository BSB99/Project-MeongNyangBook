package com.example.meongnyangbook.kafka;

import com.example.meongnyangbook.alarm.AlarmServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

  private final AlarmServiceImpl alarmService;

  @KafkaListener(topics = "alarm")
  public void consumeAlarm(AlarmRequestDto alarmRequestDto, Acknowledgment ack) {
    log.info("Consume the event {}", alarmRequestDto);
    alarmService.send(alarmRequestDto.getAlarmCategoryEnum(), alarmRequestDto.getBody(),
        alarmRequestDto.getReceiverUserId());
  }
}
