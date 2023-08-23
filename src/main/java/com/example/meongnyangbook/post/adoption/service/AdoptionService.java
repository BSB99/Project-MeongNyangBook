package com.example.meongnyangbook.post.adoption.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;


public interface AdoptionService {

  AdoptionResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto);

  AdoptionResponseDto updateAdoption(Long adoptionId, User user, AdoptionReqeustDto reqeustDto);

  ApiResponseDto deleteAdoption(Long adoptionId, User user);

  List<AdoptionResponseDto> getAdoptionList(Pageable pageable);

  AdoptionDetailResponseDto getSingleAdoption(Long adoptionId, User user);

  List<AdoptionResponseDto> getMyAdoptionPostList(User user);

  Adoption getAdoption(Long adoptionId);
}
