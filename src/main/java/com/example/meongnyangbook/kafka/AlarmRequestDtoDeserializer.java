package com.example.meongnyangbook.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import java.io.IOException;

public class AlarmRequestDtoDeserializer implements Deserializer<AlarmRequestDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AlarmRequestDto deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, AlarmRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException("AlarmRequestDto를 역직렬화하는 도중 에러가 발생했습니다.", e);
        }
    }
}

