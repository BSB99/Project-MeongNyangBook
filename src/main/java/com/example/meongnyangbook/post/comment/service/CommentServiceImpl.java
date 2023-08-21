package com.example.meongnyangbook.post.comment.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.repository.AdoptionRepository;
import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.post.comment.repository.CommentRepository;
import com.example.meongnyangbook.post.community.repository.CommunityRepository;
import com.example.meongnyangbook.post.dto.CommentRequestDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final AdoptionRepository adoptionRepository;
    private final CommunityRepository communityRepository;

    @Override
    public CommentResponseDto createComment(User user, CommentRequestDto commentRequestDto) {
        Post post = findPost(commentRequestDto.getPostId());

        Comment comment = new Comment(commentRequestDto.getContent(), post, user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long id, User user, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(id);

        comment.setContent(commentRequestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Override
    public ApiResponseDto deleteComment(Long id, User user) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
        return new ApiResponseDto("댓글이 삭제가 완료되었습니다", 200);
    }

    @Override
    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(()-> {
            throw new IllegalArgumentException("댓글이 없습니다.");
        });
    }

    @Override
    public Post findPost(Long id) { // 고쳐야됨, QueryDSL 사용 예정, 로직 수정
        if(adoptionRepository.findById(id).isPresent()) {
            return adoptionRepository.findById(id).orElseThrow(()-> {
                throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
            });
        } else if(communityRepository.findById(id).isPresent()) {
            return communityRepository.findById(id).orElseThrow(()-> {
                throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
            });
        } else {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        }
    };
}
