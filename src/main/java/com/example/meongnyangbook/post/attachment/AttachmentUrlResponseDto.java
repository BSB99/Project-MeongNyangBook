package com.example.meongnyangbook.post.attachment;

import lombok.Getter;

@Getter
public class AttachmentUrlResponseDto {

  private Long id;
  private String fileName;

  public AttachmentUrlResponseDto(AttachmentUrl url) {
    this.id = url.getId();
    this.fileName = url.getFileName();
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
