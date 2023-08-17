package com.example.meongnyangbook.post.adoption.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.adoption.repository.AdoptionRepository;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.dto.AdoptionResponseDto;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdoptionService {

    AdoptionResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto);

    AdoptionResponseDto updateAdoption(Long adoptionId, User user, AdoptionReqeustDto reqeustDto);

    ApiResponseDto deleteAdoption(Long adoptionId, User user);

    List<AdoptionResponseDto> getAdoptionList();

    AdoptionDetailResponseDto getSingleAdoption(Long adoptionId);

    Adoption getAdoption(Long adoptionId);
}
