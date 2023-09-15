package com.example.meongnyangbook.alarm.kafka;

import com.example.meongnyangbook.alarm.AlarmDto;
import com.example.meongnyangbook.alarm.AlarmServiceImpl;
import com.google.firebase.messaging.FirebaseMessagingException;
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
  public void consumeAlarm(AlarmDto alarmDto, Acknowledgment ack)
      throws FirebaseMessagingException {
    log.info("Consume the event {}", alarmDto.toString());
    alarmService.send(alarmDto.getReceiverUserId(), alarmDto.getBody(),
        alarmDto.getAlarmCategoryEnum(), alarmDto.getSenderUserName());

    // if 문을 사용해서 alarm action -> enum값 파악해서 save를 하거나 삭제를 하거나
  }
}
