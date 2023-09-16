package com.example.meongnyangbook.alarm;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

  /**
   * 알람 삭제
   *
   * @param alarmId
   * @param user
   * @return
   */
  ApiResponseDto deleteAlarmMessage(Long alarmId, User user);

  void send(Long receiverUserId, String body, AlarmCategoryEnum alarmCategoryEnum,
      String senderName);

  Alarm getAlarmComment(Long id);

  SseEmitter connectNotification(Long userId);

  Page<Alarm> alarmList(Long userId, Pageable pageable);
}
