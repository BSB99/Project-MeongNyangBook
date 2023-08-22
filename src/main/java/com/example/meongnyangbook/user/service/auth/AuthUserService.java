package com.example.meongnyangbook.user.service.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import com.example.meongnyangbook.user.jwt.JwtUtil;
import com.example.meongnyangbook.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
public class AuthUserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    @Transactional
    public ResponseEntity<ApiResponseDto> logout(User user, HttpServletRequest request, HttpServletResponse response) {
        log.info("로그아웃 서비스");
        String bearerAccessToken = jwtUtil.getJwtFromRequest(request);
        String accessToken = jwtUtil.substringToken(bearerAccessToken);

        // access token blacklist 로 저장
        log.info("액세스 토큰 블랙리스트로 저장 : " + accessToken);
        redisUtil.setBlackList(accessToken, jwtUtil.remainExpireTime(accessToken));



        return ResponseEntity.status(200).body(new ApiResponseDto("로그아웃 완료", HttpStatus.OK.value()));
    }
    @Transactional
    public ResponseEntity<ApiResponseDto> setBlockUser(User user, String blockUsername) {
        if(!user.getRole().getAuthority()
                .equals(UserRoleEnum.ADMIN.getAuthority()))    //현재 접속한 유저가 ADMIN이 아닐때
        {

            throw new IllegalArgumentException("해당 권한이 없습니다.");
        }
        log.info("권한 통과 Admin 맞음");
        //ADMIN일 시 뒤 기능 수행

        User blockUser = findUser(blockUsername);
        log.info(blockUser.getUsername());

        blockUser.setBlock(UserRoleEnum.BLOCK);     //유저 권한을 차단 설정
        log.info(blockUser.getRole().getAuthority());

        return ResponseEntity.status(200).body(new ApiResponseDto("계정 정지처리 완료", HttpStatus.OK.value()));
    }
    public User findUser(String username){
        log.info("AuthService/findUser");
        log.info("username: "+username);
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }


    public ProfileResponseDto getProfle(User user) {

        return new ProfileResponseDto(user);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> setProfile(User athenthUser, ProfileRequestDto profileRequestDto) {
        //수정 로직
        User user = findUser(athenthUser.getUsername());
        user.setProfile(profileRequestDto);

        return ResponseEntity.status(200).body(new ApiResponseDto("프로필 수정 완료", HttpStatus.OK.value()));
    }
    public ResponseEntity<ApiResponseDto> deleteAccount(User user,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response){
        logout(user,request,response);
        userRepository.delete(user);
        redisUtil.delete(user.getUsername());

        return ResponseEntity.status(200).body(new ApiResponseDto("회원 탈퇴 완료", HttpStatus.OK.value()));
    }

}
