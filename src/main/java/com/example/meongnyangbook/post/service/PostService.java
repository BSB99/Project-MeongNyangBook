package com.example.meongnyangbook.post.service;

import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  void update(Long id,
      MultipartFile[] multipartFiles, String[] deleteFileNames);
}
