package com.example.meongnyangbook.kafka;

import com.example.meongnyangbook.alarm.AlarmService;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserDetailsImpl;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class KafkaController {

  private final KafkaProducer kafkaProducer;
  private final AlarmService alarmService;

  @GetMapping("/send")
  public void send(@RequestBody AlarmDto alarmRequestDto) throws FirebaseMessagingException {
    kafkaProducer.send(alarmRequestDto);
  }

  @GetMapping(value = "/alarm/subscribe")
  public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    User user = userDetails.getUser();
    return alarmService.connectNotification(user.getId());
  }
}
