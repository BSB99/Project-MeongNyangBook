package com.example.meongnyangbook.alarm.fcm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final FcmService fcmService;

//  @PostMapping("/send")
//  public ResponseEntity<String> sendNotification(@RequestBody AlarmRequestDto alarmRequestDto) {
//    try {
//      String response = fcmService.sendMessageToToken(alarmRequestDto);
//      return ResponseEntity.ok(response);
//    } catch (FirebaseMessagingException e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//    }
//  }
}
