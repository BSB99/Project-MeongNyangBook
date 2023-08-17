package com.example.meongnyangbook.post.comment.repository;

import com.example.meongnyangbook.post.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
