package com.example.meongnyangbook.alarm.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

// FCM 설정 파일
@Component
@Slf4j
public class FirebaseInitializer {

  @Value("${firebase.key-path}")
  private String fcmKeyPath;

  @PostConstruct
  public void getFcmCredential() {
    try {
      InputStream refreshToken = new ClassPathResource(fcmKeyPath).getInputStream();

      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(refreshToken)).build();

      FirebaseApp.initializeApp(options);
      log.info("Fcm Setting Completed");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
