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
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  public List<AdoptionResponseDto> getAdoptionList() {
    List<AdoptionResponseDto> adoptionResponseDtoList = adoptionRepository.findAll()
        .stream()
        .map(AdoptionResponseDto::new)
        .collect(Collectors.toList());
    return adoptionResponseDtoList;
  }

  @Override
  public AdoptionDetailResponseDto getSingleAdoption(Long adoptionId) {
    Adoption adoption = getAdoption(adoptionId);
    return new AdoptionDetailResponseDto(adoption);
  }

  @Override
  public List<AdoptionResponseDto> getMyAdoptionPostList(User user) {
    return adoptionRepository.findByUserId(user.getId())
        .stream()
        .map(AdoptionResponseDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public Adoption getAdoption(Long adoptionId) {
    return adoptionRepository.findById(adoptionId).orElseThrow(() -> {
      throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
    });
  }


}
