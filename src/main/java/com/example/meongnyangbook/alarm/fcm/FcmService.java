package com.example.meongnyangbook.alarm.fcm;

import com.example.meongnyangbook.kafka.AlarmRequestDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    public String sendMessageToToken(AlarmRequestDto alarmRequestDto) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(new Notification(alarmRequestDto.getTitle(), alarmRequestDto.getContent()))
                .putData("userNo", alarmRequestDto.getUserNo())
                .setToken(alarmRequestDto.getToken())
                .build();
        return FirebaseMessaging.getInstance().send(message);
    }
}
