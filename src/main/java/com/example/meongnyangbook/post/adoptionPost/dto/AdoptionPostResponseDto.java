package com.example.meongnyangbook.post.adoptionPost.dto;

import com.example.meongnyangbook.post.adoptionPost.AdoptionPost;
import com.example.meongnyangbook.post.attachment.AttachmentUrlResponseDto;
import com.example.meongnyangbook.post.entity.AnimalGenderEnum;
import com.example.meongnyangbook.post.entity.AreaEnum;
import com.example.meongnyangbook.post.entity.CategoryEnum;
import lombok.Getter;

@Getter
public class AdoptionPostResponseDto {

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
  private Integer likeCount;
  private Integer commentCount;
  private AttachmentUrlResponseDto fileUrls;

  public AdoptionPostResponseDto(AdoptionPost adoptionPost) {
    this.id = adoptionPost.getId();
    this.title = adoptionPost.getTitle();
    this.description = adoptionPost.getDescription();
    this.completion = adoptionPost.getCompletion();
    this.animalName = adoptionPost.getAnimalName();
    this.animalGender = adoptionPost.getAnimalGender();
    this.animalAge = adoptionPost.getAnimalAge();
    this.area = adoptionPost.getArea();
    this.category = adoptionPost.getCategory();
    this.createdAt = adoptionPost.getCreatedAtAsString();
    this.likeCount = adoptionPost.getPostLikes().size();
    this.commentCount = adoptionPost.getCommentList().size();
    this.fileUrls = new AttachmentUrlResponseDto(adoptionPost.getAttachmentUrl());
  }

  public void setFileUrls(AttachmentUrlResponseDto fileUrls) {
    this.fileUrls = fileUrls;
  }
}
