package com.example.meongnyangbook.jwt;

import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "Jwt 검증 필터")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final RedisUtil redisUtil;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = jwtUtil.resolveToken(request);
    String refresh_token = jwtUtil.resolveRefreshToken(request);
    if (token != null) {

      if (!jwtUtil.validateToken(token)) {

        if (refresh_token != null) {
          if (jwtUtil.validateToken(refresh_token)) {
            String newAccessToken = jwtUtil.reissueAccessToken(refresh_token);
            log.info("newAccessToken" + newAccessToken);

            //프론트 구현 시 해당 newAccessToken을 Header에 넣어주기
            jwtUtil.addJwtToCookie(newAccessToken, response);// 쿠키에 새 accesstoken넣어주기
            log.info("재발급");

            //재발급 시 header에 다시 넣어줘야 하는가?
          } else {//쿠키 만료시간이 지나고 리프레쉬 토큰 만료시간이 지났을 때
            Claims info = jwtUtil.getUserInfoFromToken(refresh_token);

            //레디스의 리프레쉬 토큰 지우기
            redisUtil.delete(info.getSubject());

            // 해당 계정 로그아웃 블랙리스트 등록
            redisUtil.setBlackList(token, jwtUtil.remainExpireTime(token));

          }
        } else {//쿠키 만료시간이 지나고 리프레쉬 토큰이 없을 때
          //다시 로그인 시키기 쿠키 블랙리스트 등록(로그아웃)
          redisUtil.setBlackList(token, jwtUtil.remainExpireTime(token));

        }

        return;
      }
      //토큰 만료되지 않음
      Claims info = jwtUtil.getUserInfoFromToken(token);
      setAuthentication(info.getSubject());
    }
    try {
      filterChain.doFilter(request, response);
    } catch (FileUploadException e) {
      log.error(e.getMessage());
    }

  }

  // 인증 처리
  public void setAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(username);
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
  }

  // 인증 객체 생성
  private Authentication createAuthentication(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

}