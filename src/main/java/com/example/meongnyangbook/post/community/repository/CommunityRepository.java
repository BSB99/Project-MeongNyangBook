package com.example.meongnyangbook.post.community.repository;

import com.example.meongnyangbook.post.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Community> findByUserId(Long userId);


}
