package com.example.meongnyangbook.user.OAuth;


import com.example.meongnyangbook.jwt.JwtUtil;
import com.example.meongnyangbook.user.OAuth.google.GoogleUserService;
import com.example.meongnyangbook.user.OAuth.kakao.KakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mya/users/login")
@Tag(name = "OAuth 로그인")
public class OAuthController {

  private final KakaoService kakaoService;

  private final GoogleUserService oAuthUserService;

  @Operation(summary = "구글 로그인")
  @GetMapping("/oauth2/code/google")
  public String googleLogin(@RequestParam("code") String accessCode, HttpServletResponse response)
      throws JsonProcessingException {
    oAuthUserService.googleLogin(accessCode, response); // 반환 값이 JWT 토큰

    return "redirect:/";
  }

  @Operation(summary = "카카오 로그인")
  @GetMapping("/oauth2/code/kakao")
  public String kakaoLogin(@RequestParam String code, HttpServletResponse response)
      throws JsonProcessingException {
    String token = kakaoService.kakaoLogin(code);

    String[] spTokens = token.split(",");

    String spToken = spTokens[0].replaceAll(" ", "%20");
    Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, spToken);

    cookie.setPath("/");
    response.addCookie(cookie);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, spTokens[0]);
    response.addHeader(JwtUtil.AUTHORIZATION_REFRESH_HEADER, spTokens[1]);

    return "redirect:/";
  }

}

