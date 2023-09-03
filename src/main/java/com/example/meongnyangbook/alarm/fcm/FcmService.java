package com.example.meongnyangbook.alarm.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    public String sendMessageToToken(String token, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(new Notification(title, body))
                .setToken(token)
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }
}
