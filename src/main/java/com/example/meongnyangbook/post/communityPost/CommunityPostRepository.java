package com.example.meongnyangbook.post.communityPost;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

  Page<CommunityPost> findAllByOrderByCreatedAtDesc(Pageable pageable);

  List<CommunityPost> findByUserId(Long userId);


}
