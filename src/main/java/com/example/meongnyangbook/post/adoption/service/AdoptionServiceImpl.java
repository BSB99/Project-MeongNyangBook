package com.example.meongnyangbook.post.adoption.service;


import com.example.meongnyangbook.S3.service.S3Service;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.adoption.repository.AdoptionRepository;
import com.example.meongnyangbook.post.attachment.entity.AttachmentUrl;
import com.example.meongnyangbook.post.attachment.repository.AttachmentUrlRepository;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.post.service.PostServiceImpl;
import com.example.meongnyangbook.redis.RedisViewCountUtil;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;

import com.example.meongnyangbook.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionServiceImpl implements AdoptionService {

  private final AdoptionRepository adoptionRepository;
  private final S3Service s3Service;
  private final AttachmentUrlRepository attachmentUrlRepository;
  private final RedisViewCountUtil redisViewCountUtil;
  private final PostServiceImpl postServiceImpl;
  private final UserService userService;

  @Override
  public ApiResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto,
      MultipartFile[] multipartFiles) {

    Adoption adoption = new Adoption(reqeustDto, user);

    List<String> filePaths = s3Service.uploadFiles(multipartFiles);

    adoptionRepository.save(adoption);
    Post post = (Post) adoption;
    String fileUrls = "";
    for (String fileUrl : filePaths) {
      fileUrls = fileUrls + "," + fileUrl;
    }
    String fileUrlResult = fileUrls.replaceFirst("^,", "");
    AttachmentUrl file = new AttachmentUrl(fileUrlResult, post);

    attachmentUrlRepository.save(file);

    adoptionRepository.save(adoption);
    return new ApiResponseDto("분양 게시글 생성 완료", 201);
  }

  @Override
  @Transactional
  public AdoptionResponseDto updateAdoption(Long adoptionId, AdoptionReqeustDto requestDto,
      MultipartFile[] multipartFiles) {
    Adoption adoption = getAdoption(adoptionId);

    postServiceImpl.update(adoptionId, multipartFiles);

    //Dto 기본 내용 수정
    if (!adoption.getTitle().equals(requestDto.getTitle())) {
      adoption.setTitle(requestDto.getTitle());
    }
    if (!adoption.getDescription().equals(requestDto.getDescription())) {
      adoption.setDescription(requestDto.getDescription());
    }
    if (!adoption.getAnimalName().equals(requestDto.getAnimalName())) {
      adoption.setAnimalName(requestDto.getAnimalName());
    }
    if (!adoption.getAnimalAge().equals(requestDto.getAnimalAge())) {
      adoption.setAnimalAge(requestDto.getAnimalAge());
    }
    if (!adoption.getAnimalGender().equals(requestDto.getAnimalGender())) {
      adoption.setAnimalGender(requestDto.getAnimalGender());
    }
    if (!adoption.getArea().equals(requestDto.getArea())) {
      adoption.setArea(requestDto.getArea());
    }
    if (!adoption.getCategory().equals(requestDto.getCategory())) {
      adoption.setCategory(requestDto.getCategory());
    }
    {
      return new AdoptionResponseDto(adoption);
    }
  }


  @Override
  @Transactional
  public ApiResponseDto deleteAdoption(Long adoptionId, User user) {

    Adoption adoption = getAdoption(adoptionId);

    AttachmentUrl attachmentUrl = attachmentUrlRepository.findByPostId(adoptionId);
    String[] fileNames = attachmentUrl.getFileName().split(",");

    for (String fileName : fileNames) {
      s3Service.deleteFile(fileName);
    }

    attachmentUrlRepository.deleteByPostId(adoptionId);

    adoptionRepository.delete(adoption);

    return new ApiResponseDto("게시글 삭제가 완료되었습니다", 200);
  }

  @Override
  public List<AdoptionResponseDto> getAdoptionList(Pageable pageable) {
    List<AdoptionResponseDto> adoptionList = adoptionRepository.findAllByOrderByCreatedAtDesc(
            pageable)
        .stream()
        .map(AdoptionResponseDto::new)
        .collect(Collectors.toList());
    return adoptionList;
  }

  @Override
  @Transactional
  public AdoptionDetailResponseDto getSingleAdoption(Long adoptionId, User user) {
    Adoption adoption = getAdoption(adoptionId);

    // 조회수 증가 로직
    if (redisViewCountUtil.checkAndIncrementViewCount(adoptionId.toString(),
        user.getId().toString())) { // 조회수를 1시간이내에 올린적이 있는지 없는지 판단
      redisViewCountUtil.incrementAdoptionViewCount(adoptionId.toString());
    }

    Double viewCount = redisViewCountUtil.getViewAdoptionCount(adoptionId.toString());

    return new AdoptionDetailResponseDto(adoption, viewCount);

  }

  @Override
  public List<AdoptionResponseDto> getMyAdoptionPostList(User user) {
    return adoptionRepository.findByUserId(user.getId())
        .stream()
        .map(AdoptionResponseDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<AdoptionResponseDto> getRelativeAdoptionPostList(Long userNo) {
    User user = userService.findUser(userNo);

    return adoptionRepository.findByUserId(user.getId())
            .stream()
            .map(AdoptionResponseDto::new)
            .collect(Collectors.toList());
  };

  @Override
  public AdoptionResponseDto getBestAdoptionPost() {
    Long id = redisViewCountUtil.getTopViewedAdoptionPost();
    return new AdoptionResponseDto(getAdoption(id));
  }

  @Override
  public Adoption getAdoption(Long adoptionId) {
    return adoptionRepository.findById(adoptionId).orElseThrow(() -> {
      throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
    });
  }
}

