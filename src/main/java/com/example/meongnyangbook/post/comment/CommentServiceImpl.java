package com.example.meongnyangbook.post.comment;

import com.example.meongnyangbook.alarm.AlarmCategoryEnum;
import com.example.meongnyangbook.alarm.AlarmDto;
import com.example.meongnyangbook.alarm.AlarmRepository;
import com.example.meongnyangbook.alarm.kafka.KafkaProducer;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.dto.CommentRequestDto;
import com.example.meongnyangbook.post.dto.CommentResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.repository.PostRepository;
import com.example.meongnyangbook.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final AlarmRepository alarmRepository;
  private final KafkaProducer kafkaProducer;

  @Override
  public ApiResponseDto createComment(User user, CommentRequestDto commentRequestDto) {
    Post post = postRepository.findPostById(commentRequestDto.getPostId());

    Comment comment = new Comment(commentRequestDto.getContent(), post, user);
    commentRepository.save(comment);

    // 알람 Producer (kafka) - 게시물 receiverUserId, body, alarmCategoryEnum, senderUserId, token
    AlarmDto dto = new AlarmDto(post.getUser().getId(), commentRequestDto.getContent(),
        AlarmCategoryEnum.ALARM_COMMENT, user.getNickname());
    kafkaProducer.send(dto);

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
