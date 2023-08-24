package com.example.meongnyangbook.S3.post.s3postfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3PostFileRepository extends JpaRepository<S3PostFile, Long> {

//  List<S3PostFile> findAllById(Long id);

  void deleteByPostId(Long id);

  S3PostFile findByPostId(Long id);

  void deleteByFileName(String originalFilename);
}
