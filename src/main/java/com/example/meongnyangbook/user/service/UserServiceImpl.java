package com.example.meongnyangbook.user.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import com.example.meongnyangbook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "6rSA66as7J6Q6raM7ZWc";
    @Override
    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()) {
            return ResponseEntity.status(400).body(new ApiResponseDto("아이디가 중복됩니다.", HttpStatus.BAD_REQUEST.value()));
        }

        String nickname = requestDto.getNickname();
        Optional<User> checkNickName = userRepository.findByNickname(nickname);
        if(checkNickName.isPresent()) {
            return ResponseEntity.status(400).body(new ApiResponseDto("중복된 프로필명입니다.",HttpStatus.BAD_REQUEST.value()));
        }
        String address = requestDto.getAddress();
        String phoneNumber = requestDto.getPhoneNumber();
        String adminToken = requestDto.getAdminToken();
        if(checkAdmin(adminToken)) {
            requestDto.setAdmin(true);
        }

        UserRoleEnum role = UserRoleEnum.MEMBER;
        if(requestDto.isAdmin()) {
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                return ResponseEntity.status(400).body(new ApiResponseDto("관리자 암호가 틀려 등록이 불가능합니다.",HttpStatus.BAD_REQUEST.value()));
            }
            role = UserRoleEnum.ADMIN.ADMIN;
        }

        //Email 검증 - 필요하다면


        User user = new User(username,passwordEncoder.encode(password),nickname,address,phoneNumber, role);

        userRepository.save(user);


        return ResponseEntity.status(200).body(new ApiResponseDto("회원가입 성공", HttpStatus.OK.value()));
    }
    private boolean checkAdmin(String adminToken) {
        if(adminToken.equals(ADMIN_TOKEN)){
            return true;
        }
        return false;
    }
}
