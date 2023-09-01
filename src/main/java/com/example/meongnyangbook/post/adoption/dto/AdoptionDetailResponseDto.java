package com.example.meongnyangbook.post.adoption.dto;

import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.attachment.dto.AttachmentUrlResponseDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.post.entity.AnimalGenderEnum;
import com.example.meongnyangbook.post.entity.AreaEnum;
import com.example.meongnyangbook.post.entity.CategoryEnum;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionDetailResponseDto {

  private String title;
  private String username;
  private String description;
  private Boolean completion;
  private String animalName;
  private AnimalGenderEnum animalGender;
  private Integer animalAge;
  private AreaEnum area;
  private CategoryEnum category;
  private List<CommentResponseDto> commentList;
  private Long viewCount;
  private String createAt;
  private AttachmentUrlResponseDto fileUrls;

  public AdoptionDetailResponseDto(Adoption adoption, Double viewCount) {
    this.title = adoption.getTitle();
    this.username = adoption.getUser().getUsername();
    this.description = adoption.getDescription();
    this.completion = adoption.getCompletion();
    this.animalName = adoption.getAnimalName();
    this.animalGender = adoption.getAnimalGender();
    this.animalAge = adoption.getAnimalAge();
    this.area = adoption.getArea();
    this.category = adoption.getCategory();
    this.commentList = adoption.getCommentList()
        .stream()
        .map(CommentResponseDto::new)
        .collect(Collectors.toList());
    this.viewCount = viewCount.longValue();
    this.createAt = adoption.getCreatedAtAsString();
    this.fileUrls = new AttachmentUrlResponseDto(adoption.getAttachmentUrl());
  }
}
