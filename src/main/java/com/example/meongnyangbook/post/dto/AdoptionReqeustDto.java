package com.example.meongnyangbook.post.dto;

import com.example.meongnyangbook.post.entity.AreaEnum;
import com.example.meongnyangbook.post.entity.CategoryEnum;
import lombok.Getter;

@Getter
public class AdoptionReqeustDto {
    private String title;
    private String description;
    private Boolean completion;
    private String animalName;
    private String animalGender;
    private Integer animalAge;
    private AreaEnum area;
    private CategoryEnum category;

}
