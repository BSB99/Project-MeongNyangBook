package com.example.meongnyangbook.user.OAuth.kakao;

import com.example.meongnyangbook.user.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mya/users")
public class KakaoController {

  private final KakaoService kakaoService;

  @Operation(summary = "카카오 로그인")
  @GetMapping("/login/oauth2/code/kakao")
  public String kakaoLogin(@RequestParam String code, HttpServletResponse response)
      throws JsonProcessingException {
    String token = kakaoService.kakaoLogin(code);

    String[] spTokens = token.split(",");

    Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, spTokens[0].substring(7));
    cookie.setPath("/");
    response.addCookie(cookie);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, spTokens[0]);
    response.addHeader(JwtUtil.AUTHORIZATION_REFRESH_HEADER, spTokens[1]);

    return "redirect:/";
  }
}
