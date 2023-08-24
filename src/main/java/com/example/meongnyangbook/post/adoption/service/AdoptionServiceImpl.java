package com.example.meongnyangbook.post.adoption.service;


import com.example.meongnyangbook.S3.S3Service;
import com.example.meongnyangbook.S3.post.s3postfile.S3PostFile;
import com.example.meongnyangbook.S3.post.s3postfile.S3PostFileRepository;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.adoption.repository.AdoptionRepository;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
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
  private final S3PostFileRepository s3PostFileRepository;
  private final RedisUtil redisUtil;

  @Override
  public AdoptionResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto,
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
    S3PostFile file = new S3PostFile(fileUrlResult, post);

    s3PostFileRepository.save(file);

    adoptionRepository.save(adoption);
    return new AdoptionResponseDto(adoption);
  }

  @Override
  @Transactional
  public AdoptionResponseDto updateAdoption(Long adoptionId, User user,
      AdoptionReqeustDto reqeustDto) {
    Adoption adoption = getAdoption(adoptionId);

    // setter 사용

    return new AdoptionResponseDto(adoption);
  }


  @Override
  @Transactional
  public ApiResponseDto deleteAdoption(Long adoptionId, User user) {

    Adoption adoption = getAdoption(adoptionId);

    S3PostFile s3AdoptionFile = s3PostFileRepository.findByPostId(adoptionId);
    String[] fileNames = s3AdoptionFile.getFileName().split(",");

    for (String fileName : fileNames) {
      s3Service.deleteFile(fileName);
    }

    s3PostFileRepository.deleteByPostId(adoptionId);

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
    if (redisUtil.checkAndIncrementViewCount(adoptionId.toString(),
        user.getId().toString())) { // 조회수를 1시간이내에 올린적이 있는지 없는지 판단
      redisUtil.incrementAdoptionViewCount(adoptionId.toString());
    }

    Double viewCount = redisUtil.getViewAdoptionCount(adoptionId.toString());

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
  public AdoptionResponseDto getBestAdoptionPost() {
    Long id = redisUtil.getTopViewedAdoptionPost();
    return new AdoptionResponseDto(getAdoption(id));
  }

  @Override
  public Adoption getAdoption(Long adoptionId) {
    return adoptionRepository.findById(adoptionId).orElseThrow(() -> {
      throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
    });
  }
}

}
