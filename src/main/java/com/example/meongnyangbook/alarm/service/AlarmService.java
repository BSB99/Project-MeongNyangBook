package com.example.meongnyangbook.alarm.service;

import com.example.meongnyangbook.alarm.dto.AlarmResponseDto;
import com.example.meongnyangbook.alarm.entity.Alarm;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface AlarmService {
    List<AlarmResponseDto> showAlarmList(User user);

    ApiResponseDto deleteAlarmMessage(Long alarmId, User user);

    Alarm getAlarmComment(Long id);
}
