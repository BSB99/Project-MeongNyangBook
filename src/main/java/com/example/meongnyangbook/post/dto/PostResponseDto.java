package com.example.meongnyangbook.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
}
