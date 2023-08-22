package com.example.meongnyangbook.user.service.auth;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.PasswordRequestDto;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import com.example.meongnyangbook.user.jwt.JwtUtil;
import com.example.meongnyangbook.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
public class AuthUserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ApiResponseDto logout(User user, HttpServletRequest request, HttpServletResponse response) {
        log.info("로그아웃 서비스");
        String bearerAccessToken = jwtUtil.getJwtFromRequest(request);
        String accessToken = jwtUtil.substringToken(bearerAccessToken);

        // access token blacklist 로 저장
        log.info("액세스 토큰 블랙리스트로 저장 : " + accessToken);
        redisUtil.setBlackList(accessToken, jwtUtil.remainExpireTime(accessToken));


        return new ApiResponseDto("로그아웃 완료", HttpStatus.OK.value());
    }

    @Transactional
    public ApiResponseDto setBlockUser(User user, String blockUsername) {
        if (!user.getRole().getAuthority()
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

        return new ApiResponseDto("계정 정지처리 완료", HttpStatus.OK.value());
    }

    public ProfileResponseDto getProfle(User user) {

        return new ProfileResponseDto(user);
    }

    @Transactional
    public ApiResponseDto setProfile(User athenthUser, ProfileRequestDto profileRequestDto) {
        //수정 로직
        User user = findUser(athenthUser.getUsername());
        user.setProfile(profileRequestDto);

        return new ApiResponseDto("프로필 수정 완료", HttpStatus.OK.value());
    }

    @Transactional
    public ApiResponseDto setPassword(User user, PasswordRequestDto passwordRequestDto) {
        String currentPassword = passwordRequestDto.getPassword();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        String newPassword = passwordRequestDto.getNewPassword();
        String newPasswordCheck = passwordRequestDto.getNewPasswordCheck();

        if (!newPassword.equals(newPasswordCheck)) {
            throw new IllegalArgumentException("새로운 비밀번호가 일치하지 않습니다.");
        }

        String newEncodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newEncodedPassword);
        return new ApiResponseDto("비밀번호 변경 완료", 200);
    }

    public ApiResponseDto deleteAccount(User user,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        logout(user, request, response);
        userRepository.delete(user);
        redisUtil.delete(user.getUsername());

        return new ApiResponseDto("회원 탈퇴 완료", HttpStatus.OK.value());
    }

    public ApiResponseDto sendEmail(EmailRequestDto emailRequestDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        String content;
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(emailRequestDto.getEmail());

            if (emailRequestDto.getStatus()) {
                messageHelper.setSubject("멍냥북 - 영구 정지");

                content = "영구정지 당했습니다. <b>테스트</b>";
            } else {
                messageHelper.setSubject("멍냥북 - 새로운 비밀번호"); // 메일 제목

                User user = findUser(emailRequestDto.getEmail());

                String formattedRandomNumber = generateAndSetTemporaryPassword(user);

                content = "이메일 인증 메세지. 새로운 비밀번호: " + formattedRandomNumber;
            }

            messageHelper.setText(content, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MessagingException(e.getMessage());
        }
        return new ApiResponseDto("이메일 전송", HttpStatus.OK.value());
    }

    public User findUser(String username) {
        log.info("AuthService/findUser");
        log.info("username: " + username);
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    private String generateAndSetTemporaryPassword(User user) {
        Random random = new Random();
        // 0부터 999999 사이의 난수 생성
        int randomNumber = random.nextInt(1000000);
        // 난수를 6자리 문자열로 변환 (앞에 0을 붙여줌)
        String formattedRandomNumber = String.format("%06d", randomNumber);
        String newPassword = passwordEncoder.encode(formattedRandomNumber);
        user.setPassword(newPassword);

        return formattedRandomNumber;
    }
}
