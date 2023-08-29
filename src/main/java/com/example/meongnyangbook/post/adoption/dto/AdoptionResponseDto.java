package com.example.meongnyangbook.post.adoption.dto;

import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.attachment.dto.AttachmentUrlResponseDto;
import com.example.meongnyangbook.post.entity.AnimalGenderEnum;
import com.example.meongnyangbook.post.entity.AreaEnum;
import com.example.meongnyangbook.post.entity.CategoryEnum;
import lombok.Getter;

@Getter
public class AdoptionResponseDto {

  private Long id;
  private String title;
  private String description;
  private Boolean completion;
  private String animalName;
  private AnimalGenderEnum animalGender;
  private Integer animalAge;
  private AreaEnum area;
  private CategoryEnum category;
  private String createdAt;
  private AttachmentUrlResponseDto fileUrls;

  public AdoptionResponseDto(Adoption adoption) {
    this.id = adoption.getId();
    this.title = adoption.getTitle();
    this.description = adoption.getDescription();
    this.completion = adoption.getCompletion();
    this.animalName = adoption.getAnimalName();
    this.animalGender = adoption.getAnimalGender();
    this.animalAge = adoption.getAnimalAge();
    this.area = adoption.getArea();
    this.category = adoption.getCategory();
    this.createdAt = adoption.getCreatedAtAsString();
    this.fileUrls = new AttachmentUrlResponseDto(adoption.getAttachmentUrl());
  }
}
