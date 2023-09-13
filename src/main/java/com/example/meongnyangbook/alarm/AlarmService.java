package com.example.meongnyangbook.alarm;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

  /**
   * 알람 전체 조회
   *
   * @param user
   * @return
   */
  List<AlarmResponseDto> showAlarmList(User user);

  /**
   * 알람 삭제
   *
   * @param alarmId
   * @param user
   * @return
   */
  ApiResponseDto deleteAlarmMessage(Long alarmId, User user);

  Alarm getAlarmComment(Long id);

  SseEmitter connectNotification(Long userId);
}
