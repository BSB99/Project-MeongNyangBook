package com.example.meongnyangbook.user.OAuth.kakao;


import com.example.meongnyangbook.jwt.JwtUtil;
import com.example.meongnyangbook.post.attachment.AttachmentUrlRepository;
import com.example.meongnyangbook.user.OAuth.OAuthProviderEnum;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserRepository;
import com.example.meongnyangbook.user.UserRoleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RestTemplate restTemplate;
  private final JwtUtil jwtUtil;
  @Value("${client-id}")
  private String clientId;
  private final AttachmentUrlRepository attachmentUrlRepository;

  public String kakaoLogin(String code) throws JsonProcessingException {
    // 1. "인가 코드"로 "액세스 토큰" 요청
    String accessToken = getToken(code);

    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
    // 3. 필요시 회원가입
    User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
    //JWT토큰 반환
    String token = jwtUtil.createToken(kakaoUser.getUsername(), kakaoUser.getRole(),
        OAuthProviderEnum.KAKAO);
    String refreshToken = jwtUtil.createRefreshToken(kakaoUser.getUsername(), kakaoUser.getRole());

    token = token + "," + refreshToken;

    return token;
  }

  private String getToken(String code) throws JsonProcessingException {

    // 요청 URL 만들기
    URI uri = UriComponentsBuilder
        .fromUriString("https://kauth.kakao.com")
        .path("/oauth/token")
        .encode()
        .build()
        .toUri();

    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HTTP Body 생성
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", clientId);
    body.add("redirect_uri", "https://meongnyangbook.site/mya/users/login/oauth2/code/kakao");
    body.add("code", code);

    RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
        .post(uri)
        .headers(headers)
        .body(body);

    // HTTP 요청 보내기
    ResponseEntity<String> response = restTemplate.exchange(
        requestEntity,
        String.class
    );

    // HTTP 응답 (JSON) -> 액세스 토큰 파싱
    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
    return jsonNode.get("access_token").asText();
  }

  private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
    log.info("엑세스토큰 : " + accessToken);
    // 요청 URL 만들기
    URI uri = UriComponentsBuilder
        .fromUriString("https://kapi.kakao.com")
        .path("/v2/user/me")
        .encode()
        .build()
        .toUri();

    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
        .post(uri)
        .headers(headers)
        .body(new LinkedMultiValueMap<>());

    // HTTP 요청 보내기
    ResponseEntity<String> response = restTemplate.exchange(
        requestEntity,
        String.class
    );

    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
    Long id = jsonNode.get("id").asLong();
    String nickname = jsonNode.get("properties")
        .get("nickname").asText();
    String email = jsonNode.get("kakao_account")
        .get("email").asText();

    return new KakaoUserInfoDto(id, nickname, email);
  }

  private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
    // DB 에 중복된 Kakao Id 가 있는지 확인
    String kakaoUserId = kakaoUserInfo.getId().toString();
    User kakaoUser = userRepository.findByUsername(kakaoUserId).orElse(null);

    if (kakaoUser == null) {
      // 신규 회원가입
      // password: random UUID
      String password = UUID.randomUUID().toString();
      String encodedPassword = passwordEncoder.encode(password);

      kakaoUser = new User(kakaoUserId, encodedPassword, kakaoUserInfo.getEmail(), "자기소개", "SEOUL",
          null,
          UserRoleEnum.MEMBER, OAuthProviderEnum.KAKAO);
      kakaoUser.setProfileImgurl("emptyFile");
      userRepository.save(kakaoUser);


    }
    return kakaoUser;
  }
}
