package com.example.meongnyangbook.user.OAuth;


import com.example.meongnyangbook.user.OAuth.google.GoogleUserService;
import com.example.meongnyangbook.user.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mya/users/login")
public class OAuthController {

  private final GoogleUserService oAuthUserService;
  private final JwtUtil jwtUtil;

  @GetMapping("/oauth2/code/google")
  public String googleLogin(@RequestParam("code") String accessCode, HttpServletResponse response)
      throws JsonProcessingException {
    oAuthUserService.googleLogin(accessCode, response); // 반환 값이 JWT 토큰

    return "redirect:/";
  }

  @GetMapping
  public String home() {
    return "chatchat";
  }
}

