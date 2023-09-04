package com.example.meongnyangbook.post.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentUrlRepository extends JpaRepository<AttachmentUrl, Long> {

  void deleteByPostId(Long id);

  AttachmentUrl findByPostId(Long id);

  void deleteByFileName(String originalFilename);
}
