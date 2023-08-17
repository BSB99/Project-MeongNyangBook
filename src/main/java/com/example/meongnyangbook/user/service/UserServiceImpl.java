package com.example.meongnyangbook.user.service;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.dto.EmailRequestDto;
import com.example.meongnyangbook.user.dto.LoginRequestDto;
import com.example.meongnyangbook.user.dto.PhoneRequestDto;
import com.example.meongnyangbook.user.dto.SignupRequestDto;
import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import com.example.meongnyangbook.user.jwt.JwtUtil;
import com.example.meongnyangbook.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Value("${database.username}")
    private String ADMIN_TOKEN;
    private final RedisUtil redisUtil;

    @Value("${coolsms.devHee.apikey}")
    private String apiKey;

    @Value("${coolsms.devHee.apisecret}")
    private String apiSecret;

    @Value("${coolsms.devHee.fromnumber}")
    private String fromNumber;

    private final JavaMailSender javaMailSender;

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
            role = UserRoleEnum.ADMIN;
        }

        //Email 검증 - 필요하다면

        User user = new User(username, passwordEncoder.encode(password), nickname, address, phoneNumber, role);

        userRepository.save(user);


        return ResponseEntity.status(200).body(new ApiResponseDto("회원가입 성공", HttpStatus.OK.value()));
    }




    @Override
    public ResponseEntity<ApiResponseDto> signin(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("로그인 시도");
        String username = loginRequestDto.getUsername();
        Optional<User> user = userRepository.findByUsername(username);

        if(!user.isPresent()){
            return ResponseEntity.status(400).body(new ApiResponseDto("해당유저가 없습니다.", HttpStatus.FORBIDDEN.value()));
        }
        String password = loginRequestDto.getPassword();

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }
        // Access Token 생성 및 헤더에 추가
        String accessToken = jwtUtil.createToken(user.get().getUsername() ,user.get().getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        jwtUtil.addJwtToCookie(accessToken,response);

        return ResponseEntity.status(200).body(new ApiResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    @Override
    public ApiResponseDto sendMessage(PhoneRequestDto phoneRequestDto) throws CoolsmsException {
        Random random = new Random();

        // 0부터 9999 사이의 난수 생성
        int randomNumber = random.nextInt(10000);

        // 난수를 4자리 문자열로 변환 (앞에 0을 붙여줌)
        String formattedRandomNumber = String.format("%04d", randomNumber);

        redisUtil.set(phoneRequestDto.getPhoneNumber(), formattedRandomNumber, 3);

        Message coolsms = new Message(apiKey, apiSecret);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("to", phoneRequestDto.getPhoneNumber());
            params.put("from", fromNumber);
            params.put("type", "SMS");
            params.put("text", "[grabMe] 인증번호 "+formattedRandomNumber+" 를 입력하세요.");
            params.put("app_version", "test app 1.2"); // application name and version
            try {
                JSONObject obj = coolsms.send(params);
                System.out.println(obj.toString());

            } catch (CoolsmsException e) {
                throw new CoolsmsException(e.getMessage(), e.getCode());
            }

        return new ApiResponseDto("핸드폰 인증번호 전송", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto authMessageCode(PhoneRequestDto phoneRequestDto) {
        Object getCode = redisUtil.get(phoneRequestDto.getPhoneNumber());

        if (getCode == null) {
            throw new IllegalArgumentException("만료시간이 지났습니다.");
        }

        int code = Integer.parseInt((String) getCode);

        if(code == phoneRequestDto.getCode()) {
            redisUtil.delete(phoneRequestDto.getPhoneNumber());
        } else {
            throw new IllegalArgumentException("인증 코드가 다릅니다.");
        }

        return new ApiResponseDto("핸드폰 인증번호 확인", HttpStatus.OK.value());
    };

    @Override
    public ApiResponseDto sendEmail(EmailRequestDto emailRequestDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        String content;
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(emailRequestDto.getEmail());

            if(emailRequestDto.getStatus()) {
                messageHelper.setSubject("회원 영구 정지");

                content = "영구정지 당했습니다. <b>테스트</b>";
            } else {
                messageHelper.setSubject("이메일 인증 메세지");

                content = "이메일 인증 메세지. <b>테스트</b>";
            }

            messageHelper.setText(content, true);

            javaMailSender.send(message);
        } catch(Exception e){
            throw new MessagingException(e.getMessage());
        }
        return new ApiResponseDto("이메일 전송", HttpStatus.OK.value());
    }

    @Override
    public boolean checkAdmin(String adminToken) {
        if(adminToken.equals(ADMIN_TOKEN)){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void logout(User user, HttpServletRequest request, HttpServletResponse response) {
        log.info("로그아웃 서비스");
        String bearerAccessToken = jwtUtil.getJwtFromRequest(request);
        String accessToken = jwtUtil.substringToken(bearerAccessToken);
//        String username = user.getUsername();



        // access token blacklist 로 저장
        log.info("액세스 토큰 블랙리스트로 저장 : " + accessToken);
        redisUtil.setBlackList(accessToken, jwtUtil.remainExpireTime(accessToken));
    }
}
