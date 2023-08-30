package com.example.meongnyangbook.post.community.service;

import com.example.meongnyangbook.S3.service.S3Service;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.attachment.entity.AttachmentUrl;
import com.example.meongnyangbook.post.attachment.repository.AttachmentUrlRepository;
import com.example.meongnyangbook.post.community.dto.CommunityDetailResponseDto;
import com.example.meongnyangbook.post.community.dto.CommunityResponseDto;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.community.repository.CommunityRepository;
import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.service.PostServiceImpl;
import com.example.meongnyangbook.redis.RedisViewCountUtil;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService {

  private final CommunityRepository communityRepository;
  private final S3Service s3Service;
  private final RedisViewCountUtil redisViewCountUtil;
  private final PostServiceImpl postServiceImpl;
  private final AttachmentUrlRepository attachmentUrlRepository;

  @Override
  public ApiResponseDto createCommunity(PostRequestDto requestDto, User user,
      MultipartFile[] multipartFiles) {
    Community community = new Community(requestDto, user);

    List<String> filePaths = s3Service.uploadFiles(multipartFiles);

    communityRepository.save(community);

    Post post = (Post) community;

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
  public CommunityResponseDto updateCommunity(Long communityNo, PostRequestDto requestDto,
      MultipartFile[] multipartFiles) {

    Community community = getCommunity(communityNo);

    postServiceImpl.update(communityNo, multipartFiles);

    if (!community.getTitle().equals(requestDto.getTitle())) {
      community.setTitle(requestDto.getTitle());
    }
    if (!community.getDescription().equals(requestDto.getDescription())) {
      community.setDescription(requestDto.getDescription());
    }

    return new CommunityResponseDto(community);
  }


  @Override
  @Transactional
  public ApiResponseDto deleteCommunity(Long communityNo) {

    Community community = getCommunity(communityNo);
    AttachmentUrl attachmentUrl = attachmentUrlRepository.findByPostId(communityNo);
    String[] fileNames = attachmentUrl.getFileName().split(",");
    for (String fileName : fileNames) {
      s3Service.deleteFile(fileName);
    }
    attachmentUrlRepository.deleteByPostId(communityNo);
    communityRepository.delete(community);

    return new ApiResponseDto("게시글 삭제가 완료되었습니다.", 200);
  }

  @Override
  public List<CommunityResponseDto> getCommunityList(Pageable pageable) {
    Page<Community> communityList = communityRepository.findAllByOrderByCreatedAtDesc(pageable);

    return communityList.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CommunityDetailResponseDto getOneCommunity(Long communityNo, User user) {
    Community community = getCommunity(communityNo);

    if (redisViewCountUtil.checkAndIncrementViewCount(communityNo.toString(),
        user.getId().toString())) {
      redisViewCountUtil.incrementCommunityViewCount(communityNo.toString());
    }
    Double viewCount = redisViewCountUtil.getViewCommunityCount(communityNo.toString());
    return new CommunityDetailResponseDto(community, viewCount);
  }

  @Override
  public CommunityResponseDto getBestAdoptionPost() {
    Long id = redisViewCountUtil.getTopViewedCommunityPost();
    return new CommunityResponseDto(getCommunity(id));
  }

  @Override
  public List<CommunityResponseDto> getMyCommunityPostList(User user) {
    return communityRepository.findByUserId(user.getId())
        .stream()
        .map(CommunityResponseDto::new)
        .toList();
  }


  @Override
  public Community getCommunity(Long communityNo) {
    return communityRepository.findById(communityNo).orElseThrow(() -> {
      throw new IllegalArgumentException("게시글이 존재하지 않습니다");
    });
  }
}
