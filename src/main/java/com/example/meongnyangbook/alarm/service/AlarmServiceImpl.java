package com.example.meongnyangbook.alarm.service;

import com.example.meongnyangbook.alarm.dto.AlarmResponseDto;
import com.example.meongnyangbook.alarm.entity.Alarm;
import com.example.meongnyangbook.alarm.repository.AlarmRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

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
}
