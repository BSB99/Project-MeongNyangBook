package com.example.meongnyangbook.alarm.fcm;

import com.example.meongnyangbook.kafka.AlarmRequestDto;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final FcmService fcmService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody AlarmRequestDto alarmRequestDto) {
        try {
            String response = fcmService.sendMessageToToken(alarmRequestDto);
            return ResponseEntity.ok(response);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
