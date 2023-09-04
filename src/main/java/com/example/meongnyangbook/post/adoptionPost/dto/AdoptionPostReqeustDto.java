package com.example.meongnyangbook.post.adoptionPost.dto;

import com.example.meongnyangbook.post.entity.AnimalGenderEnum;
import com.example.meongnyangbook.post.entity.AreaEnum;
import com.example.meongnyangbook.post.entity.CategoryEnum;
import lombok.Getter;

@Getter
public class AdoptionPostReqeustDto {

  private String title;
  private String description;
  private Boolean completion = false;
  private String animalName;
  private AnimalGenderEnum animalGender;
  private Integer animalAge;
  private AreaEnum area;
  private CategoryEnum category;
}
