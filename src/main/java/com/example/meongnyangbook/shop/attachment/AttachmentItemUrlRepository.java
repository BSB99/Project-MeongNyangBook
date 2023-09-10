package com.example.meongnyangbook.shop.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentItemUrlRepository extends JpaRepository<AttachmentItemUrl, Long> {

  AttachmentItemUrl findByItemId(Long itemNo);

  void deleteByItemId(Long itemId);
}
