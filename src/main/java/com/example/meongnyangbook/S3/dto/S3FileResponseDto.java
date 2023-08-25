package com.example.meongnyangbook.S3.dto;

import lombok.Getter;

@Getter
public class S3FileResponseDto {

  private String fileName;
  private Long fileId;

//  public S3FileResponseDto(S3UserFile s3UserFile) {
//    this.fileName = s3UserFile.getFileName();
//    this.fileId = s3UserFile.getFileId();
//  }
}