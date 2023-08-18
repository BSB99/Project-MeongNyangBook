package com.example.meongnyangbook.post.repository;

import com.example.meongnyangbook.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본 CRUD 메서드를 상속받아야 합니다.
}
