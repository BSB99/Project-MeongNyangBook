package com.example.meongnyangbook.post.service;

import com.example.meongnyangbook.S3.post.S3PostFile;
import com.example.meongnyangbook.S3.post.S3PostFileRepository;
import com.example.meongnyangbook.S3.service.S3Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final S3PostFileRepository s3PostFileRepository;
  private final S3Service s3Service;

  @Override
  @Transactional
  public void update(Long id,
      MultipartFile[] multipartFiles, String[] deleteFileNames) {

    S3PostFile s3PostFile = s3PostFileRepository.findByPostId(id);

    String[] filenames = s3PostFile.getFileName().split(",");
    String deleteAfterFileNames = "";

    for (String filename : filenames) {
      for (String deleteFileName : deleteFileNames) {
        if (!filename.contains(deleteFileName)) {
          deleteAfterFileNames = deleteAfterFileNames + "," + filename;
        } else {
          s3Service.deleteFile(filename);
        }
      }
    }
    List<String> uploadFileNames = s3Service.uploadFiles(multipartFiles);

    String combineUploadFileName = s3Service.CombineString(uploadFileNames);

    String replaceDeleteAfterFileName = deleteAfterFileNames.replaceFirst("^,", "");
    String replaceUploadFileName = combineUploadFileName.replaceFirst("^,", "");
    String result = (replaceDeleteAfterFileName + "," + replaceUploadFileName).replaceFirst("^,",
        "");
    s3PostFile.setFileName(result);
  }
}
