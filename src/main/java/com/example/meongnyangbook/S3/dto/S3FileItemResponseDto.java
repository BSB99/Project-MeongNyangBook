package com.example.meongnyangbook.S3.dto;

import com.example.meongnyangbook.shop.attachment.AttachmentItemUrl;
import lombok.Getter;

import java.nio.file.Files;
import java.util.List;

@Getter
public class S3FileItemResponseDto {

  private String fileName;
  private Long id;

  public S3FileItemResponseDto(AttachmentItemUrl s3ItemListFile) {
      this.id = s3ItemListFile.getId();
      this.fileName = s3ItemListFile.getFileName();
  }
}