package com.example.meongnyangbook.post.adoptionPost;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionPostRepository extends JpaRepository<AdoptionPost, Long> {

  Page<AdoptionPost> findAllByOrderByCreatedAtDesc(Pageable pageable);

  List<AdoptionPost> findByUserId(Long userId);
}
