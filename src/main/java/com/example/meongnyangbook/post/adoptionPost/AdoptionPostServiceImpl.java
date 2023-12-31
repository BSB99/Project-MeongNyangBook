package com.example.meongnyangbook.post.adoptionPost;

import com.example.meongnyangbook.S3.S3Service;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostDetailResponseDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostReqeustDto;
import com.example.meongnyangbook.post.adoptionPost.dto.AdoptionPostResponseDto;
import com.example.meongnyangbook.post.attachment.AttachmentUrl;
import com.example.meongnyangbook.post.attachment.AttachmentUrlRepository;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.service.PostServiceImpl;
import com.example.meongnyangbook.redis.RedisViewCountUtil;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionPostServiceImpl implements AdoptionPostService {

  private final AdoptionPostRepository adoptionPostRepository;
  private final S3Service s3Service;
  private final AttachmentUrlRepository attachmentUrlRepository;
  private final RedisViewCountUtil redisViewCountUtil;
  private final PostServiceImpl postServiceImpl;
  private final UserService userService;


  @Override
  public ApiResponseDto createAdoption(User user, AdoptionPostReqeustDto reqeustDto,
      MultipartFile[] multipartFiles) {

    AdoptionPost adoptionPost = new AdoptionPost(reqeustDto, user);

    List<String> filePaths = s3Service.uploadFiles(multipartFiles);

    adoptionPostRepository.save(adoptionPost);
    Post post = (Post) adoptionPost;
    String fileUrls = "";
    for (String fileUrl : filePaths) {
      fileUrls = fileUrls + "," + fileUrl;
    }
    String fileUrlResult = fileUrls.replaceFirst("^,", "");
    AttachmentUrl file = new AttachmentUrl(fileUrlResult, post);

    attachmentUrlRepository.save(file);

    adoptionPostRepository.save(adoptionPost);
    return new ApiResponseDto("분양 게시글 생성 완료", 201);
  }

  @Override
  @Transactional
  public AdoptionPostResponseDto updateAdoption(Long adoptionId, AdoptionPostReqeustDto requestDto,
      MultipartFile[] multipartFiles) {
    AdoptionPost adoptionPost = getAdoption(adoptionId);

    postServiceImpl.update(adoptionId, multipartFiles);
    adoptionPost.update(requestDto);

    return new AdoptionPostResponseDto(adoptionPost);
  }


  @Override
  @Transactional
  public ApiResponseDto deleteAdoption(Long adoptionId, User user) {

    AdoptionPost adoptionPost = getAdoption(adoptionId);

    AttachmentUrl attachmentUrl = attachmentUrlRepository.findByPostId(adoptionId);
    String[] fileNames = attachmentUrl.getFileName().split(",");

    for (String fileName : fileNames) {
      s3Service.deleteFile(fileName);
    }

    attachmentUrlRepository.deleteByPostId(adoptionId);

    adoptionPostRepository.delete(adoptionPost);

    return new ApiResponseDto("게시글 삭제가 완료되었습니다", 200);
  }

  @Override
  public Page<AdoptionPostResponseDto> getAdoptionList(Pageable pageable) {
    Page<AdoptionPostResponseDto> adoptionList = adoptionPostRepository.findAllByOrderByCreatedAtDesc(
            pageable)
        .map(AdoptionPostResponseDto::new);

//    for (AdoptionPostResponseDto postList : adoptionList) {
//      String[] fileNames = postList.getFileUrls().getFileName().split(",")[0].split("/");
//
//      String resizeS3FileName = resizeS3FirstName + fileNames[fileNames.length - 1];
//
//      postList.getFileUrls().setFileName(resizeS3FileName);
//    }

    return adoptionList;
  }

  @Override
  @Transactional
  public AdoptionPostDetailResponseDto getSingleAdoption(Long adoptionId, User user) {
    AdoptionPost adoptionPost = getAdoption(adoptionId);

    // 조회수 증가 로직
    if (redisViewCountUtil.checkAndIncrementViewCount(adoptionId.toString(),
        user.getId().toString())) { // 조회수를 1시간이내에 올린적이 있는지 없는지 판단
      redisViewCountUtil.incrementPostViewCount(adoptionId.toString());
    }

    Double viewCount = redisViewCountUtil.getViewPostCount(adoptionId.toString());

    return new AdoptionPostDetailResponseDto(adoptionPost, viewCount);

  }

  @Override
  public List<AdoptionPostResponseDto> getMyAdoptionPostList(User user) {
    return adoptionPostRepository.findByUserId(user.getId())
        .stream()
        .map(AdoptionPostResponseDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<AdoptionPostResponseDto> getRelativeAdoptionPostList(Long userNo) {
    User user = userService.findUser(userNo);

    return adoptionPostRepository.findByUserId(user.getId())
        .stream()
        .map(AdoptionPostResponseDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public AdoptionPost getAdoption(Long adoptionId) {
    return adoptionPostRepository.findById(adoptionId).orElseThrow(() -> {
      throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
    });
  }


  @Override
  @Transactional
  public void adoptionCheck(User user, Long postId) {

    Optional<AdoptionPost> post = adoptionPostRepository.findById(postId);

    post.get().setIsAdoptions(true);


  }
}