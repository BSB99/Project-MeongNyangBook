package com.example.meongnyangbook.alarm;

import com.example.meongnyangbook.alarm.fcm.FcmService;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.kafka.AlarmRequestDto;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

  private final AlarmRepository alarmRepository;
  private final UserRepository userRepository;
  private final FcmService fcmService;

  @Override
  public List<AlarmResponseDto> showAlarmList(User user) {
    List<AlarmResponseDto> alarmList = alarmRepository.findByUserId(user.getId())
        .stream()
        .map(AlarmResponseDto::new)
        .toList();
    return alarmList;
  }

  @Override
  @Transactional
  public ApiResponseDto deleteAlarmMessage(Long alarmId, User user) {
    Alarm alarmComment = getAlarmComment(alarmId);
    alarmRepository.delete(alarmComment);

    return new ApiResponseDto("알림 메시지가 삭제 되었습니다.", 200);
  }

  @Override
  public Alarm getAlarmComment(Long id) {
    return alarmRepository.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("삭제할 알림 메시지가 없습니다.");
    });
  }

  public void send(AlarmCategoryEnum alarmCategoryEnum, String body, Long receiverUserId) {
    User user = userRepository.findById(receiverUserId).orElseThrow(IllegalArgumentException::new);

    // alarm save
    Alarm alarm = new Alarm(body, "1", user, alarmCategoryEnum);
    alarmRepository.save(alarm);

    // FCM
    Long num = 2L;
    AlarmRequestDto alarmRequestDto = new AlarmRequestDto(receiverUserId, body, alarmCategoryEnum,
        num, "token");
    fcmService.sendMessageToToken(alarmRequestDto); // Return 값이 있어서 안됨 -> 생각 필요
  }
}
