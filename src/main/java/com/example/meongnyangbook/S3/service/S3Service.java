package com.example.meongnyangbook.S3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "S3Service")
public class S3Service {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  @Value("${cloud.aws.region.static}")
  private String region;

  public List<String> uploadFiles(MultipartFile[] files) {
    List<String> fileNames = new ArrayList<>();

    for (MultipartFile file : files) {
//            String fileName = file.getOriginalFilename();
      String fileName = UUID.randomUUID() + file.getOriginalFilename(); // fileName을 난수와 함께 저장
      String fileUrl = S3FileUpload(file, fileName);
      fileNames.add(fileUrl);
    }

    return fileNames;
  }

  public String uploadFile(MultipartFile files) {
    String fileNames;
//            String fileName = file.getOriginalFilename();
    String fileName = UUID.randomUUID() + files.getOriginalFilename(); // fileName을 난수와 함께 저장
    String fileUrl = S3FileUpload(files, fileName);
    fileNames = fileUrl;

    return fileNames;
  }

  public String S3FileUpload(MultipartFile multipartFile, String fileName) {
    String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(multipartFile.getSize());
    metadata.setContentType(multipartFile.getContentType());
    try {
      amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
      return fileUrl;
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("이미지의 크기가 너무 큽니다");
    }
  }

  public String CombineString(List<String> stringList) {
    String result = "";
    for (String str : stringList) {
      result = result + "," + str;
    }
    return result;
  }

  public void deleteFile(String originalFilename) {
    String fileName = originalFilename.substring(originalFilename.lastIndexOf("/") + 1);
    log.info(fileName);
    amazonS3.deleteObject(bucket, fileName);
  }
}