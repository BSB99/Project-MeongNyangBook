package com.example.meongnyangbook.post.adoptionPost.dto;

import com.example.meongnyangbook.post.adoptionPost.AdoptionPost;
import com.example.meongnyangbook.post.attachment.AttachmentUrlResponseDto;
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
public class AdoptionPostDetailResponseDto {

  private Long userId;
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
  private Integer likesCount;
  private AttachmentUrlResponseDto fileUrls;
  private Integer commentSize;

  public AdoptionPostDetailResponseDto(AdoptionPost adoptionPost, Double viewCount) {
    this.userId = adoptionPost.getUser().getId();
    this.title = adoptionPost.getTitle();
    this.username = adoptionPost.getUser().getUsername();
    this.description = adoptionPost.getDescription();
    this.completion = adoptionPost.getCompletion();
    this.animalName = adoptionPost.getAnimalName();
    this.animalGender = adoptionPost.getAnimalGender();
    this.animalAge = adoptionPost.getAnimalAge();
    this.area = adoptionPost.getArea();
    this.category = adoptionPost.getCategory();
    this.commentList = adoptionPost.getCommentList()
        .stream()
        .map(CommentResponseDto::new)
        .collect(Collectors.toList());
    this.viewCount = viewCount.longValue();
    this.createAt = adoptionPost.getCreatedAtAsString();
    this.likesCount = adoptionPost.getPostLikes().size();
    this.fileUrls = new AttachmentUrlResponseDto(adoptionPost.getAttachmentUrl());
  }
}
