package com.example.meongnyangbook.alarm;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmCategoryEnum {
  ALARM_LIKE("new Like"),
  ALARM_COMMENT("new Comment"),
  ALARM_MESSAGE("new Message");

  private final String alarmText;
}
