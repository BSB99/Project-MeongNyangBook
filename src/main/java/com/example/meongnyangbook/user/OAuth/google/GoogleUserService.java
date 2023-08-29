package com.example.meongnyangbook.user.OAuth.google;

import com.example.meongnyangbook.user.OAuth.OAuthProviderEnum;
import com.example.meongnyangbook.user.entity.User;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import com.example.meongnyangbook.user.jwt.JwtUtil;
import com.example.meongnyangbook.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleUserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  private String secretId;

  @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
  private String redirectUri;


  public void googleLogin(String code, HttpServletResponse response)
      throws JsonProcessingException {
    // "인가 코드"로 "액세스 토큰" 요청
    String accessToken = getToken(code);
    log.info("googleLogin accessToken: " + accessToken);

    // 토큰으로 구글 API 호출 : "액세스 토큰"으로 "구글 사용자 정보" 가져오기
    GoogleInfoResponse googleUserInfo = getUserInfo(accessToken);

    // 회원가입 또는 로그인
    User googleUser = saveOrUpdate(googleUserInfo);

    // token 생성 및 헤더에 추가
    String token = jwtUtil.createToken(googleUser.getUsername(), UserRoleEnum.MEMBER,
        OAuthProviderEnum.GOOGLE);
    jwtUtil.addJwtToCookie(token, response);
  }


  public String getToken(String code) throws JsonProcessingException {

    RestTemplate restTemplate = new RestTemplate();

    log.info("getToken code: " + code);
    // 요청 URL 만들기
    URI uri = UriComponentsBuilder
        .fromUriString("https://oauth2.googleapis.com")
        .path("/token")
        .encode()
        .build()
        .toUri();

    // HTTP Header 생성: application/x-www-form-urlencoded 형식의 본문을 가진 POST 요청을 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded");

    // HTTP Body 생성
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", clientId);
    params.add("client_secret", secretId);
    params.add("redirect_uri", redirectUri);
    params.add("code", code);

    RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
        .post(uri)
        .headers(headers)
        .body(params);

    // HTTP 요청 보내기
    ResponseEntity<String> response = restTemplate.exchange(
        requestEntity,
        String.class // 반환값 타입은 String
    );

    // HTTP 응답 (JSON) -> 액세스 토큰 값을 반환합니다.
    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
    return jsonNode.get("access_token").asText();
  }

  private GoogleInfoResponse getUserInfo(String accessToken) throws JsonProcessingException {
    RestTemplate restTemplate = new RestTemplate();

    String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);

    ResponseEntity<String> response = restTemplate.exchange(
        userInfoUrl,
        HttpMethod.GET,
        new HttpEntity<String>("", headers),
        String.class
    );

    ObjectMapper mapper = new ObjectMapper();
    GoogleInfoResponse googleInfo = mapper.readValue(response.getBody(), GoogleInfoResponse.class);
    return googleInfo;
  }

  private User saveOrUpdate(GoogleInfoResponse googleInfo) {
    Optional<User> existingUserOpt = userRepository.findByUsername(googleInfo.getUsername());

    if (existingUserOpt.isPresent()) {
      // 이미 존재하는 사용자라면 그대로 반환
      return existingUserOpt.get();
    } else {
      // 존재하지 않는 사용자라면 새로 생성
      String randomPassword = UUID.randomUUID().toString();

      User newUser = User.builder()
          .username(googleInfo.getUsername())
          .nickname(googleInfo.getNickname())
          .password(passwordEncoder.encode(randomPassword))
          .role(UserRoleEnum.MEMBER)
          .oAuthProvider(OAuthProviderEnum.GOOGLE)
          .build();

      return userRepository.save(newUser);
    }
  }
}
