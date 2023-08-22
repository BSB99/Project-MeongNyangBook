package com.example.meongnyangbook.post.repository;

import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryQuery {
}
