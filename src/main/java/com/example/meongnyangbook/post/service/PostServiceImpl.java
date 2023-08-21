package com.example.meongnyangbook.post.service;

import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.like.entity.PostLike;
import com.example.meongnyangbook.post.like.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.meongnyangbook.post.repository.PostRepository;
import com.example.meongnyangbook.user.entity.User;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostLikeRepository postLikeRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
    }

    @Override
    public boolean hasLiked(User user, Post post) {
        return postLikeRepository.existsByUserAndPost(user, post);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public void toggleLike(User user, Post post) {
        boolean hasLiked = postLikeRepository.existsByUserAndPost(user, post);
        if (hasLiked) {
            postLikeRepository.deleteByUserAndPost(user, post);
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    @Override
    public List<Post> getPostsByMostLikes() {
        return postRepository.findPostsByMostLikes();
    }
}
