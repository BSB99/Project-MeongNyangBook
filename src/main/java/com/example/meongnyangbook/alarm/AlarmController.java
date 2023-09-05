package com.example.meongnyangbook.alarm;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mya/alarms")
public class AlarmController {

  private final AlarmService alarmService;

  @GetMapping
  public ResponseEntity<List<AlarmResponseDto>> showAlarmList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<AlarmResponseDto> result = alarmService.showAlarmList(userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);

  }

  @DeleteMapping("/{alarmId}")
  public ResponseEntity<ApiResponseDto> deleteAlarmMessage(@PathVariable Long alarmId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = alarmService.deleteAlarmMessage(alarmId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
