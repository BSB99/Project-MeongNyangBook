package com.example.meongnyangbook.post.comment.service;

import com.example.meongnyangbook.alarm.entity.Alarm;
import com.example.meongnyangbook.alarm.entity.AlarmCategoryEnum;
import com.example.meongnyangbook.alarm.repository.AlarmRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.comment.entity.Comment;
import com.example.meongnyangbook.post.comment.repository.CommentRepository;
import com.example.meongnyangbook.post.dto.CommentRequestDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.repository.jpa.PostRepository;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final AlarmRepository alarmRepository;

  @Override
  public ApiResponseDto createComment(User user, CommentRequestDto commentRequestDto) {
    Post post = postRepository.findPostById(commentRequestDto.getPostId());

    Comment comment = new Comment(commentRequestDto.getContent(), post, user);
    commentRepository.save(comment);

    // AlarmComment DB에 저장
    Alarm alarmComment = new Alarm(post.getTitle(), commentRequestDto.getContent(),
        user.getNickname(), post.getUser(), AlarmCategoryEnum.ALARM_COMMENT);

    alarmRepository.save(alarmComment);

    return new ApiResponseDto("댓글 작성 성공", 201);
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
    return commentRepository.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("댓글이 없습니다.");
    });
  }
}
