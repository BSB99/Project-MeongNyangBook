package com.example.meongnyangbook.post.service;

import com.example.meongnyangbook.S3.service.S3Service;
import com.example.meongnyangbook.post.attachment.entity.AttachmentUrl;
import com.example.meongnyangbook.post.attachment.repository.AttachmentUrlRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final AttachmentUrlRepository s3PostFileRepository;
  private final S3Service s3Service;

  @Override
  @Transactional
  public void update(Long id,
      MultipartFile[] multipartFiles, String[] deleteFileNames) {

    AttachmentUrl attachmentUrl = s3PostFileRepository.findByPostId(id);

    String[] filenames = attachmentUrl.getFileName().split(",");
    String deleteAfterFileNames = "";

    int sizeCheck = 0;
    for (String filename : filenames) {
      for (String deleteFileName : deleteFileNames) {
        if (!filename.contains(deleteFileName)) {
          deleteAfterFileNames = deleteAfterFileNames + "," + filename;
          sizeCheck++;
        } else {
          s3Service.deleteFile(filename);
        }
      }
    }
    List<String> uploadFileNames = s3Service.uploadFiles(multipartFiles);
    if ((uploadFileNames.size() + sizeCheck) > 5) {
      throw new IllegalArgumentException("사진의 최대개수는 5개 입니다.");
    }
    String combineUploadFileName = s3Service.CombineString(uploadFileNames);

    String replaceDeleteAfterFileName = deleteAfterFileNames.replaceFirst("^,", "");
    String replaceUploadFileName = combineUploadFileName.replaceFirst("^,", "");
    String result = (replaceDeleteAfterFileName + "," + replaceUploadFileName).replaceFirst("^,",
        "");

    attachmentUrl.setFileName(result);
  }
}
