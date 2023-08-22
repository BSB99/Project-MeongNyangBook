package com.example.meongnyangbook.alarm.controller;

import com.example.meongnyangbook.alarm.dto.AlarmResponseDto;
import com.example.meongnyangbook.alarm.service.AlarmService;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ResponseEntity<List<AlarmResponseDto>> showAlarmList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<AlarmResponseDto> result = alarmService.showAlarmList(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @DeleteMapping("/{alarmId}")
    public ResponseEntity<ApiResponseDto> deleteAlarmMessage(@PathVariable Long alarmId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto result = alarmService.deleteAlarmMessage(alarmId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
