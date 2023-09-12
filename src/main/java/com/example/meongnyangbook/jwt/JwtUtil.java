package com.example.meongnyangbook.jwt;

import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.OAuth.OAuthProviderEnum;
import com.example.meongnyangbook.user.User;
import com.example.meongnyangbook.user.UserRepository;
import com.example.meongnyangbook.user.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final UserRepository userRepository;

  // Header KEY 값
  public static final String AUTHORIZATION_HEADER = "Authorization";
  // Header의 refresh
  public static final String AUTHORIZATION_REFRESH_HEADER = "Authorization_Refresh";
  // 사용자 권한 값의 KEY
  public static final String AUTHORIZATION_KEY = "auth";
  // Token 식별자
  public static final String BEARER_PREFIX = "Bearer ";
  // 토큰 만료시간
  private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

  @Value("${jwt.secret.key}")// Base64 Encoded SecretKey
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final RedisUtil redisUtil;

  public static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일


  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  // 토큰 생성
  public String createToken(String username, UserRoleEnum role, OAuthProviderEnum providerEnum) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username) // 사용자 식별자값(ID)
            .claim(AUTHORIZATION_KEY, role)  //사용자 권한
            .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
            .claim("provider", providerEnum)
            .setIssuedAt(date) // 발급일
            .signWith(key, signatureAlgorithm) // 암호화 알고리즘
            .compact();


  }

  // refresh token 생성
  public String createRefreshToken(String username, UserRoleEnum role) {
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + REFRESH_TOKEN_TIME);

    String refreshToken = JwtUtil.BEARER_PREFIX + Jwts.builder()
        .setSubject(username) // 사용자 식별자값(ID)
        .claim(AUTHORIZATION_KEY, role)  //사용자 권한
        .setExpiration(expireDate)  //만료시간
        .signWith(key, signatureAlgorithm)
        .compact();

    return refreshToken;
  }

  // access token 재발급
  public String reissueAccessToken(String refresh) {

    Claims info = getUserInfoFromToken(refresh);

    String username = info.getSubject();

    // refresh token 가져오기
//            String refreshToken = redisUtil.getRefreshToken(username);

    // refresh token 존재하고 유효하다면

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    String newAccessToken = createToken(username, user.getRole(), user.getOAuthProvider());
    log.info(newAccessToken);
    return newAccessToken;
  }


  // JWT 검증
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      if (redisUtil.hasKeyBlackList(token)) {
        // TODO 에러 발생시키는 부분 수정
        throw new RuntimeException("로그아웃한 토큰");
      }
      return true;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }

  // Cookie에 저장된 JWT 토근을 Substring
  public String substringToken(String tokenValue) {
    if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
      return tokenValue.substring(7);
    }
    log.error("Not Found Token");
    throw new NullPointerException("Not Found Token");
  }

  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public void addJwtToCookie(String token, HttpServletResponse response) {
    try {
      token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

      Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
      cookie.setPath("/");

      response.addCookie(cookie);
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage());
    }
  }

  // HttpServletRequest 에서 Cookie Value : JWT 가져오기
  public String getJwtFromRequest(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
          try {
            return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
          } catch (UnsupportedEncodingException e) {
            return null;
          }
        }
      }
    }
    return null;
  }

  //  토큰 남은 만료 시간
  public Long remainExpireTime(String token) {
    // 토큰 만료 시간
    Long expirationTime = getUserInfoFromToken(token).getExpiration().getTime();
    // 현재 시간
    Long dateTime = new Date().getTime();

    return expirationTime - dateTime;
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    // token 이 null 또는 공백인지 체크 && 토큰이 정상적으로 Bearer 를 가지고 있는지 체크
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7); // JWT 토큰 substring
    }
    return null;
  }

  //refresh 토큰을 헤더에서 찾아서 Bearer을 잘라주는 메서드
  public String resolveRefreshToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_REFRESH_HEADER);
    // token 이 null 또는 공백인지 체크 && 토큰이 정상적으로 Bearer 를 가지고 있는지 체크
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7); // JWT 토큰 substring
    }
    return null;
  }
}