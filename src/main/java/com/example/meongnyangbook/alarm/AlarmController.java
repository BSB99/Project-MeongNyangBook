package com.example.meongnyangbook.alarm;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Tag(name = "알람 기능 API")
@RequiredArgsConstructor
@RequestMapping("/mya/sse")
public class AlarmController {

  private final AlarmService alarmService;


  @GetMapping(value = "/alarm/subscribe")
  public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    User user = userDetails.getUser();
    return alarmService.connectNotification(user.getId());
  }

  @GetMapping("/alarm")
  public ResponseEntity<Page<AlarmResponseDto>> getAlarm(Pageable pageable,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    User user = userDetails.getUser();
    Page<AlarmResponseDto> result = alarmService.alarmList(user.getId(), pageable)
        .map(AlarmResponseDto::new);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/{alarmId}")
  public ResponseEntity<ApiResponseDto> deleteAlarmMessage(@PathVariable Long alarmId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = alarmService.deleteAlarmMessage(alarmId, userDetails.getUser());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
