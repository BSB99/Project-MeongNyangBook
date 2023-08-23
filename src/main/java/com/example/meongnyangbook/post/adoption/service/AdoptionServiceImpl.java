package com.example.meongnyangbook.post.adoption.service;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.adoption.repository.AdoptionRepository;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionServiceImpl implements AdoptionService {

  private final AdoptionRepository adoptionRepository;
  private final RedisUtil redisUtil;

  @Override
  public AdoptionResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto) {
    Adoption adoption = new Adoption(reqeustDto, user);
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

    adoptionRepository.delete(adoption);

    return new ApiResponseDto("게시글 삭제가 완료되었습니다", 200);
  }

  @Override
  public List<AdoptionResponseDto> getAdoptionList(Pageable pageable) {
    List<AdoptionResponseDto> adoptionList = adoptionRepository.findAllByOrderByCreatedAtDesc(pageable)
        .stream()
        .map(AdoptionResponseDto::new)
        .collect(Collectors.toList());
    return adoptionList;
  }

  @Override
  public AdoptionDetailResponseDto getSingleAdoption(Long adoptionId, User user) {
    Adoption adoption = getAdoption(adoptionId);

    // 조회수 증가 로직
    String redisUserKey = user.getId().toString();
    Long viewCount = adoption.getViewCount();

    redisUtil.set(redisUserKey, adoptionId.toString(), 60);
    if (!redisUtil.hasKey(redisUserKey)) {
        Long newView = viewCount + 1;
        adoptionRepository.

    }

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
