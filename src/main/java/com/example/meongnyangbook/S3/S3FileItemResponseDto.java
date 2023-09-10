package com.example.meongnyangbook.S3;

import com.example.meongnyangbook.shop.attachment.AttachmentItemUrl;
import lombok.Getter;

@Getter
public class S3FileItemResponseDto {

  private String fileName;
  private Long id;

  public S3FileItemResponseDto(AttachmentItemUrl s3ItemListFile) {
    this.id = s3ItemListFile.getId();
    this.fileName = s3ItemListFile.getFileName();
  }
}