package com.example.meongnyangbook.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, AlarmRequestDto> kafkaTemplate;
    public void send(AlarmRequestDto alarmRequestDto) {
        kafkaTemplate.send("board_topic", alarmRequestDto);
    }
}