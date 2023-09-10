package com.example.meongnyangbook.user.auth;

import com.example.meongnyangbook.S3.S3Service;
import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.jwt.JwtUtil;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserRepository;
import com.example.meongnyangbook.user.UserRoleEnum;
import com.example.meongnyangbook.user.UserService;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.PasswordRequestDto;
import com.example.meongnyangbook.user.dto.ProfileRequestDto;
import com.example.meongnyangbook.user.dto.ProfileResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
public class AuthUserServiceImpl implements AuthUserService {

  private final UserRepository userRepository;
  private final RedisUtil redisUtil;
  private final JwtUtil jwtUtil;
  private final JavaMailSender javaMailSender;
  private final PasswordEncoder passwordEncoder;
  private final S3Service s3Service;
  private final UserService userService;

  @Override
  @Transactional
  public ApiResponseDto logout(User user, HttpServletRequest request,
      HttpServletResponse response) {
    log.info("로그아웃 서비스");

    String bearerAccessToken = jwtUtil.getJwtFromRequest(request);
    String accessToken = jwtUtil.substringToken(bearerAccessToken);
    log.info(accessToken);

    // access token blacklist 로 저장
    log.info("액세스 토큰 블랙리스트로 저장 : " + accessToken);
    redisUtil.setBlackList(accessToken, jwtUtil.remainExpireTime(accessToken));
    return new ApiResponseDto("로그아웃 완료", HttpStatus.OK.value());

  }

  @Override
  @Transactional
  public ApiResponseDto setBlockUser(User user, String blockUsername) {
    if (!user.getRole().getAuthority()
        .equals(UserRoleEnum.ADMIN.getAuthority()))    //현재 접속한 유저가 ADMIN이 아닐때
    {

      throw new IllegalArgumentException("해당 권한이 없습니다.");
    }
    //ADMIN일 시 뒤 기능 수행

    User blockUser = findUser(blockUsername);

    blockUser.setBlock(UserRoleEnum.BLOCK);     //유저 권한을 차단 설정

    return new ApiResponseDto("계정 정지처리 완료", HttpStatus.OK.value());
  }

  public ProfileResponseDto getProfle(User user) {

    return new ProfileResponseDto(user);
  }

  public ProfileResponseDto getSingleProfle(Long userNo) {
    User user = userService.findUser(userNo);

    return new ProfileResponseDto(user);
  }

  ;

  @Override
  @Transactional
  public ApiResponseDto setProfile(User athenthUser, ProfileRequestDto profileRequestDto,
      MultipartFile multipartFiles) {
    User user = findUser(athenthUser.getUsername());
    //AWS에 저
    String filePath = s3Service.uploadFile(multipartFiles);

    user.setProfileImgurl(filePath);
    //수정 로직
    user.setProfile(profileRequestDto);

    return new ApiResponseDto("프로필 수정 완료", HttpStatus.OK.value());
  }

  @Override
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

  @Override
  @Transactional
  public ApiResponseDto deleteAccount(User detailsUser,
      HttpServletRequest request,
      HttpServletResponse response) {
    User user = findUser(detailsUser.getUsername());

    s3Service.deleteFile(user.getProfileImgurl());

    logout(user, request, response);
    userRepository.delete(user);
    redisUtil.delete(user.getUsername());

    return new ApiResponseDto("회원 탈퇴 완료", HttpStatus.OK.value());
  }

//  @Override
//  public ApiResponseDto deleteFile(User user, String fileName) {
//
//    S3UserFile s3UserFile = s3UserFileRepository.findByFileName(fileName).orElseThrow(() -> {
//      throw new IllegalArgumentException("삭제할 파일이 없음");
//    });
//
//    s3UserFileRepository.delete(s3UserFile);
//    s3Service.deleteFile(user.getProfileImgurl());
//
//    return new ApiResponseDto("첨부 파일 삭제 완료", HttpStatus.OK.value());
//  }

  @Override
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

  @Override
  public User findUser(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
  }

  @Override
  public String generateAndSetTemporaryPassword(User user) {
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
