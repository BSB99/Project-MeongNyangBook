package com.example.meongnyangbook.alarm;

//import com.example.meongnyangbook.alarm.fcm.FcmService;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {

  private final AlarmRepository alarmRepository;
  private final UserRepository userRepository;
  private final EmitterRepository emitterRepository;
  //  private final FcmService fcmService;
  private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

//  @Override
//  public List<AlarmResponseDto> showAlarmList(User user) {
//    List<AlarmResponseDto> alarmList = alarmRepository.findByUserId(user.getId())
//        .stream()
//        .map(AlarmResponseDto::new)
//        .toList();
//    return alarmList;
//  }

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


  @Override
  public void send(Long receiverUserId, String body, AlarmCategoryEnum alarmCategoryEnum,
      String senderName) {
    User user = userRepository.findById(receiverUserId).orElseThrow(IllegalArgumentException::new);
    // alarm save
    Alarm alarm = new Alarm(body, senderName, user, alarmCategoryEnum);
    alarmRepository.save(alarm);

    // SSE
    emitterRepository.get(receiverUserId).ifPresentOrElse(it -> {
          try {
            it.send(SseEmitter.event()
                .id(alarm.getId().toString())
                .name("alarm")
                .data("new Alarm"));
            log.info("emitter success");
          } catch (IOException exception) {
            emitterRepository.delete(receiverUserId);
            log.info("emitter error");
            throw new IllegalArgumentException(exception);
//                SimpleSnsApplicationException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
          }
        },
        () -> log.info("No emitter founded")
    );
//    fcmService.sendMessageToToken(alarmDto); // Return 값이 있어서 안됨 -> 생각 필요

  }

  @Override
  public SseEmitter connectNotification(Long userId) {
    SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
    emitterRepository.save(userId, emitter);
    emitter.onCompletion(() -> emitterRepository.delete(userId));
    emitter.onTimeout(() -> emitterRepository.delete(userId));

    try {
      log.info("send");
      emitter.send(SseEmitter.event()
          .id("id")
          .name("alarm")
          .data("connect completed"));
    } catch (IOException exception) {
      throw new IllegalArgumentException(exception);
//          SimpleSnsApplicationException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
    }
    return emitter;
  }

  @Override
  public Page<Alarm> alarmList(Long userId, Pageable pageable) {
    return alarmRepository.findAllByUserId(userId, pageable);
  }

}
