package com.example.meongnyangbook.kafka;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

  private final KafkaProducer kafkaProducer;

  @GetMapping("/send")
  public void send(@RequestBody AlarmDto alarmRequestDto) throws FirebaseMessagingException {
    kafkaProducer.send(alarmRequestDto);
  }
}
