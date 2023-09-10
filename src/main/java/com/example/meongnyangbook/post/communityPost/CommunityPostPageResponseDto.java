package com.example.meongnyangbook.post.communityPost;

import java.util.List;
import lombok.Getter;

@Getter
public class CommunityPostPageResponseDto {

  Long len;
  List<CommunityPostResponseDto> responseList;

  public CommunityPostPageResponseDto(Long len, List<CommunityPostResponseDto> responseList) {
    this.len = len;
    this.responseList = responseList;
  }
}
