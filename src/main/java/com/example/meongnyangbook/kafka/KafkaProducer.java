package com.example.meongnyangbook.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<Long, AlarmDto> kafkaTemplate;

  public void send(AlarmDto alarmDto) {
    kafkaTemplate.send("alarm", alarmDto.getReceiverUserId(), alarmDto);
    log.info("Send to Kafka finished");
  }
}