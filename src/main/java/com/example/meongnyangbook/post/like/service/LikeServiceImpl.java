package com.example.meongnyangbook.post.like.service;

import com.example.meongnyangbook.alarm.entity.Alarm;
import com.example.meongnyangbook.alarm.entity.AlarmCategoryEnum;
import com.example.meongnyangbook.alarm.repository.AlarmRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.like.entity.PostLike;
import com.example.meongnyangbook.post.like.repository.LikeRepository;
import com.example.meongnyangbook.post.repository.jpa.PostRepository;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final AlarmRepository alarmRepository;


  @Override
  public ApiResponseDto createPostLike(Long postId, User user) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
    if (likeRepository.existsByPostIdAndUserId(postId, user.getId())) {
      //좋아요를 이미 누른 유저
      throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
    }
    PostLike postLike = PostLike.builder().post(post).user(user).build();
    likeRepository.save(postLike);

    // AlarmLike의 DB에 저장
    Alarm alarmLike = new Alarm(post.getTitle(), "좋아요!", user.getNickname(), post.getUser(),
        AlarmCategoryEnum.ALARM_LIKE);
    alarmRepository.save(alarmLike);

    return new ApiResponseDto("좋아요", HttpStatus.CREATED.value());
  }

  @Override
  public ApiResponseDto deletePostLike(Long postId, User user) {

    if (likeRepository.existsByPostIdAndUserId(postId, user.getId())) {
      PostLike postLike = likeRepository.findByPostIdAndUserId(postId, user.getId());
      likeRepository.delete(postLike);
    } else {
      throw new IllegalArgumentException("해당 게시글 좋아요를 누르지 않음");
    }

    return new ApiResponseDto("좋아요 취소", HttpStatus.OK.value());
  }
}
