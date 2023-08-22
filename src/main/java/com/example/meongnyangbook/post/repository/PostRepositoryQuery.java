package com.example.meongnyangbook.post.repository;

import com.example.meongnyangbook.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryQuery {
    Post findPostById(Long postId);
}