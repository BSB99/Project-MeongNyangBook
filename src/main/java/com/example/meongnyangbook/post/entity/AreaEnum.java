package com.example.meongnyangbook.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AreaEnum {
    SEOUL("서울특별시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    INCHEON("인천광역시"),
    GWANGJU("광주광역시"),
    DAEGEON("대전광역시"),
    ULSAN("울산광역시"),
    SEJONG("세종특별자치시"),
    JEJU("제주특별자치도");

    private final String description;
}
