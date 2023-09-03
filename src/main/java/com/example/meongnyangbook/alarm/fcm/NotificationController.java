package com.example.meongnyangbook.alarm.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final FcmService fcmService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String token,
                                                   @RequestParam String title,
                                                   @RequestParam String body) {
        try {
            String response = fcmService.sendMessageToToken(token, title, body);
            System.out.println(token);
            return ResponseEntity.ok(response);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
