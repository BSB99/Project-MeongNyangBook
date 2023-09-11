package com.example.meongnyangbook.alarm.fcm;

import com.example.meongnyangbook.kafka.AlarmDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

  public void sendMessageToToken(AlarmDto alarmDto)
      throws FirebaseMessagingException {
    Message message = Message.builder()
        .setNotification(new Notification(alarmDto.getBody(), alarmDto.getSenderUserName()))
        .putData("userNo", alarmDto.getReceiverUserId().toString())
        .setToken(alarmDto.getToken())
        .build();
    FirebaseMessaging.getInstance().send(message);
  }
}
