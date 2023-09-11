//package com.example.meongnyangbook.kafka;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.apache.kafka.common.serialization.Serializer;
//
//public class AlarmRequestDtoSerializer implements Serializer<AlarmRequestDto> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public byte[] serialize(String topic, AlarmRequestDto data) {
//        try {
//            return objectMapper.writeValueAsBytes(data);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("AlarmRequestDto를 직렬화하는 도중 에러가 발생했습니다.", e);
//        }
//    }
//}
