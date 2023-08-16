package com.example.meongnyangbook.post.community.repository;

import com.example.meongnyangbook.post.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
