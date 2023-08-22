package com.example.meongnyangbook.post.like.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.like.entity.PostLike;
import com.example.meongnyangbook.post.like.repository.LikeRepository;
import com.example.meongnyangbook.post.repository.PostRepository;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;


    public ResponseEntity<ApiResponseDto> createPostLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        if(likeRepository.existsByPostIdAndUserId(postId,user.getId())){
            //좋아요를 이미 누른 유저

            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
        PostLike postLike = PostLike.builder().post(post).user(user).build();
        likeRepository.save(postLike);


        return ResponseEntity.status(200).body(new ApiResponseDto("좋아요", HttpStatus.OK.value()));
    }

    public ResponseEntity<ApiResponseDto> deletePostLike(Long postId, User user) {

        if(likeRepository.existsByPostIdAndUserId(postId,user.getId())) {
            PostLike postLike = likeRepository.findByPostIdAndUserId(postId, user.getId());
            likeRepository.delete(postLike);
        }
        else {
            throw new  IllegalArgumentException("해당 게시글 좋아요를 누르지 않음");
        }

        return ResponseEntity.status(200).body(new ApiResponseDto("좋아요 취소", HttpStatus.OK.value()));
    }
}
