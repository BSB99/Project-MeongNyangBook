package com.example.meongnyangbook.post.adoption.service;


import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.adoption.repository.AdoptionRepository;
import com.example.meongnyangbook.post.adoption.dto.AdoptionDetailResponseDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.adoption.dto.AdoptionResponseDto;
import com.example.meongnyangbook.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService{

    private final AdoptionRepository adoptionRepository;

    @Override
    public AdoptionResponseDto createAdoption(User user, AdoptionReqeustDto reqeustDto) {
        Adoption adoption = new Adoption(reqeustDto, user);
        adoptionRepository.save(adoption);
        return new AdoptionResponseDto(adoption);
    }

    @Override
    @Transactional
    public AdoptionResponseDto updateAdoption(Long adoptionId, User user, AdoptionReqeustDto reqeustDto) {
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
    public Adoption getAdoption(Long adoptionId) {
        return adoptionRepository.findById(adoptionId).orElseThrow(() -> {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        });
    }


}
