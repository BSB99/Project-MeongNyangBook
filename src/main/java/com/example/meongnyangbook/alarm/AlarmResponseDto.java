package com.example.meongnyangbook.alarm;

import lombok.Getter;

@Getter
public class AlarmResponseDto {

  private Long id;
  private String body;
  private String sender;
  private String createdAt;

  public AlarmResponseDto(Alarm alarm) {
    this.id = alarm.getId();
    this.body = alarm.getBody();
    this.sender = alarm.getSender();
    this.createdAt = alarm.getCreatedAtAsString();
  }
}
