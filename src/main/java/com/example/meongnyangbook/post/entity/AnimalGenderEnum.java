package com.example.meongnyangbook.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnimalGenderEnum {
    MALE("수컷"),
    FEMALE("암컷");

    private final String description;
}
