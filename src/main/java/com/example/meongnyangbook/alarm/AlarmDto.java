package com.example.meongnyangbook.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlarmDto {

  private Long receiverUserId;
  private String body;
  private AlarmCategoryEnum alarmCategoryEnum;
  private String senderUserName;
  // enum action -> create, delete
}
