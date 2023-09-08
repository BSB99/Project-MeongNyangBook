package com.example.meongnyangbook.post.adoptionPost.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class AdoptionPostPageResponseDto {

  Long len;
  List<AdoptionPostResponseDto> responseList;

  public AdoptionPostPageResponseDto(Long len, List<AdoptionPostResponseDto> responseList) {
    this.len = len;
    this.responseList = responseList;
  }
}
