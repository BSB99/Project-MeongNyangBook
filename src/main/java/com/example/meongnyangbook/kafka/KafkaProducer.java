package com.example.meongnyangbook.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<Long, AlarmRequestDto> kafkaTemplate;

  public void send(AlarmRequestDto alarmRequestDto) {
    kafkaTemplate.send("alarm", alarmRequestDto.getReceiverUserId(), alarmRequestDto);
    log.info("Send to Kafka finished");
  }
}