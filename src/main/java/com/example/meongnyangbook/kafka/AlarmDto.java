package com.example.meongnyangbook.kafka;

import com.example.meongnyangbook.alarm.AlarmCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {

  private Long receiverUserId;
  private String body;
  private AlarmCategoryEnum alarmCategoryEnum;
  private String senderUserName;
  private String token;
}
