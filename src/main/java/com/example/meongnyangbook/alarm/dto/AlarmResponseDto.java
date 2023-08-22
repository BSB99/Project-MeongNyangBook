package com.example.meongnyangbook.alarm.dto;

import com.example.meongnyangbook.alarm.entity.Alarm;
import lombok.Getter;

@Getter
public class AlarmResponseDto {

    private Long id;
    private String alarmTitle;
    private String alarmBody;
    private String alarmSender;
    private String createdAt;

    public AlarmResponseDto(Alarm alarm) {
        this.id = alarm.getId();
        this.alarmTitle = alarm.getAlarmTitle();
        this.alarmBody = alarm.getAlarmBody();
        this.alarmSender = alarm.getAlarmSender();
        this.createdAt = alarm.getCreatedAtAsString();
    }
}
