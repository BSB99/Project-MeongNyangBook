package com.example.meongnyangbook.post.communityPost;

import com.example.meongnyangbook.S3.S3Service;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.attachment.AttachmentUrl;
import com.example.meongnyangbook.post.attachment.AttachmentUrlRepository;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.like.LikeRepository;
import com.example.meongnyangbook.post.service.PostServiceImpl;
import com.example.meongnyangbook.redis.RedisViewCountUtil;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CommunityPostServiceImpl implements CommunityPostService {

  private final CommunityPostRepository communityPostRepository;
  private final S3Service s3Service;
  private final RedisViewCountUtil redisViewCountUtil;
  private final PostServiceImpl postServiceImpl;
  private final AttachmentUrlRepository attachmentUrlRepository;
  private final UserService userService;
  private final LikeRepository likeRepository;

  @Override
  public ApiResponseDto createCommunity(PostRequestDto requestDto, User user,
      MultipartFile[] multipartFiles) {
    CommunityPost communityPost = new CommunityPost(requestDto, user);

    List<String> filePaths = s3Service.uploadFiles(multipartFiles);

    communityPostRepository.save(communityPost);

    Post post = (Post) communityPost;

    String fileUrls = "";
    for (String fileUrl : filePaths) {
      fileUrls = fileUrls + "," + fileUrl;
    }
    String fileUrlResult = fileUrls.replaceFirst("^,", "");
    AttachmentUrl file = new AttachmentUrl(fileUrlResult, post);

    attachmentUrlRepository.save(file);

    return new ApiResponseDto("커뮤니티 게시글 생성", 201);
  }


  @Override
  @Transactional
  public CommunityPostResponseDto updateCommunity(Long communityNo, PostRequestDto requestDto,
      MultipartFile[] multipartFiles) {

    CommunityPost communityPost = getCommunity(communityNo);

    postServiceImpl.update(communityNo, multipartFiles);

    communityPost.setTitle(requestDto.getTitle());
    communityPost.setDescription(requestDto.getDescription());

    return new CommunityPostResponseDto(communityPost);
  }


  @Override
  @Transactional
  public ApiResponseDto deleteCommunity(Long communityNo) {

    CommunityPost communityPost = getCommunity(communityNo);
    AttachmentUrl attachmentUrl = attachmentUrlRepository.findByPostId(communityNo);
    String[] fileNames = attachmentUrl.getFileName().split(",");
    for (String fileName : fileNames) {
      s3Service.deleteFile(fileName);
    }
    attachmentUrlRepository.deleteByPostId(communityNo);
    communityPostRepository.delete(communityPost);

    return new ApiResponseDto("게시글 삭제가 완료되었습니다.", 200);
  }

  @Override
  public Page<CommunityPostResponseDto> getCommunityList(Pageable pageable) {
    Page<CommunityPostResponseDto> communityPage = communityPostRepository.findAllByOrderByCreatedAtDesc(
            pageable)
        .map(CommunityPostResponseDto::new);

    Long count = communityPostRepository.count();

    return communityPage;
  }

  @Override
  @Transactional
  public CommunityPostDetailResponseDto getOneCommunity(Long communityNo, User user) {
    CommunityPost communityPost = getCommunity(communityNo);

    if (redisViewCountUtil.checkAndIncrementViewCount(communityNo.toString(),
        user.getId().toString())) {
      redisViewCountUtil.incrementPostViewCount(communityNo.toString());
    }
    Double viewCount = redisViewCountUtil.getViewPostCount(communityNo.toString());
    return new CommunityPostDetailResponseDto(communityPost, viewCount);
  }

  @Override
  public List<CommunityPostResponseDto> getMyCommunityPostList(User user) {
    return communityPostRepository.findByUserId(user.getId())
        .stream()
        .map(CommunityPostResponseDto::new)
        .toList();
  }

  @Override
  public List<CommunityPostResponseDto> getRelativeCommunityPostList(Long userNo) {
    User user = userService.findUser(userNo);

    List<CommunityPostResponseDto> commuDtoList = communityPostRepository.findByUserId(user.getId())
        .stream()
        .map(CommunityPostResponseDto::new)
        .toList();
    return commuDtoList;
  }

  @Override
  public List<CommunityPostResponseDto> getBestCommunityPost() {
    List<CommunityPostResponseDto> bests = communityPostRepository.findTop3ByOrderByScoreDesc()
        .stream()
        .map(CommunityPostResponseDto::new)
        .toList();

    return bests;
  }

  ;


  @Override
  public CommunityPost getCommunity(Long communityNo) {
    return communityPostRepository.findById(communityNo).orElseThrow(() -> {
      throw new IllegalArgumentException("게시글이 존재하지 않습니다");
    });
  }

}
