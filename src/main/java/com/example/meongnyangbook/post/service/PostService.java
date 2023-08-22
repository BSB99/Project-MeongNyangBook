package com.example.meongnyangbook.post.service;

import com.example.meongnyangbook.post.entity.Post;

import com.example.meongnyangbook.user.entity.User;

import java.util.List;

public interface PostService {
    boolean hasLiked(User user, Post post);

    Post getPostById(Long postId);

    void toggleLike(User user, Post post);

    List<Post> getPostsByMostLikes();
}

