package com.example.meongnyangbook.kafka;
import com.example.meongnyangbook.alarm.fcm.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final FcmService fcmService;

    @KafkaListener(topics = "board_topic")
    public void listen(AlarmRequestDto alarmRequestDto) throws FirebaseMessagingException {
        fcmService.sendMessageToToken(alarmRequestDto);
        System.out.println("Received message: " + alarmRequestDto.getContent());
    }
}
