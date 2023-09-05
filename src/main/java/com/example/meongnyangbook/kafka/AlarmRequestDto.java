package com.example.meongnyangbook.kafka;

import lombok.Getter;

@Getter
public class AlarmRequestDto {
    private String userNo;
    private String title;
    private String content;
    private String token;
}
