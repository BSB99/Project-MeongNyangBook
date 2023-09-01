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
      MultipartFile[] multipartFiles) {

    AttachmentUrl attachmentUrl = s3PostFileRepository.findByPostId(id);
    String[] fileList = attachmentUrl.getFileName().split(",");
    for (String file : fileList) {
      s3Service.deleteFile(file);
    }
    attachmentUrl.setFileName("");

    if ((multipartFiles.length) > 5) {
      throw new IllegalArgumentException("사진의 최대개수는 5개 입니다.");
    }
    List<String> uploadFileNames = s3Service.uploadFiles(multipartFiles);
    String combineUploadFileName = s3Service.CombineString(uploadFileNames);

    String replaceUploadFileName = combineUploadFileName.replaceFirst("^,", "");
    String result = (replaceUploadFileName).replaceFirst("^,",
        "");

    attachmentUrl.setFileName(result);
  }
}
