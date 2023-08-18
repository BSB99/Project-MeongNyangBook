package com.example.meongnyangbook.user.jwt;

import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.details.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "Jwt 검증 필터")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisUtil redisUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);
        String refresh_token = jwtUtil.resolveRefreshToken(request);
        if(token != null) {
            log.info("액세스 토큰 값 : " + token);
            log.info("리프레쉬 토큰 값 : " + refresh_token);

            if(!jwtUtil.validateToken(token)){

                if(refresh_token != null) {
                    if (jwtUtil.validateToken(refresh_token)) {
                        String newAccessToken = jwtUtil.reissueAccessToken(refresh_token);
                        log.info("newAccessToken" + newAccessToken);

                        //프론트 구현 시 해당 newAccessToken을 Header에 넣어주기

                        jwtUtil.addJwtToCookie(newAccessToken, response);// 쿠키에 새 accesstoken넣어주기
                        log.info("재발급");
                        //재발급 시 header에 다시 넣어줘야 하는가?
                    }
                    else{//쿠키 만료시간이 지나고 리프레쉬 토큰 만료시간이 지났을 때
                        Claims info = jwtUtil.getUserInfoFromToken(refresh_token);

                        //레디스의 리프레쉬 토큰 지우기
                        redisUtil.delete(info.getSubject());

                        // 해당 계정 로그아웃 블랙리스트 등록
                        redisUtil.setBlackList(token, jwtUtil.remainExpireTime(token));

                        //더 좋은 방법
                        //쿠키 지우기(프론트에서 지우는게 편하다)
                    }
                }
                else{//쿠키 만료시간이 지나고 리프레쉬 토큰이 없을 때

                    redisUtil.setBlackList(token, jwtUtil.remainExpireTime(token));
                    //다시 로그인 시키기 쿠키 블랙리스트 등록(로그아웃)
                    //쿠키 삭제해주기
                }

                return;
            }
            else {
                log.info("토큰 만료되지 않음");
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        try {
            log.info("AuthFilter -> filterChain");
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