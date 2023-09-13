package com.example.meongnyangbook.config;

import com.example.meongnyangbook.jwt.JwtAuthorizationFilter;
import com.example.meongnyangbook.jwt.JwtUtil;
import com.example.meongnyangbook.redis.RedisUtil;
import com.example.meongnyangbook.user.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final RedisUtil redisUtil;

  public WebSecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService,
      AuthenticationConfiguration authenticationConfiguration, RedisUtil redisUtil) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.authenticationConfiguration = authenticationConfiguration;
    this.redisUtil = redisUtil;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtUtil, userDetailsService, redisUtil);
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // CSRF 설정
    http.csrf((csrf) -> csrf.disable());

    // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
    http.sessionManagement((sessionManagement) ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    );

    http.authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll() // resources 접근 허용 설정
                .requestMatchers("/img/**", "/fonts/**", "/assets/**").permitAll()
                .requestMatchers("/").permitAll() // '/' 로 접근 허용
                .requestMatchers("/mya/chats/**", "/mya-websocket").permitAll() // websocket 허용
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // swagger 허용
                .requestMatchers("/mya/users/**").permitAll() // 'mya/users/' 허용
                .requestMatchers(HttpMethod.GET, "/mya/items", "/mya/communities", "/mya/adoptions",
                    "/mya/adoptions", "/mya/adoptions/best-post", "/mya/reviews/**",
                    "/mya/inquiries/**", "/mya/items/search")
                .permitAll() // 조회 허용
                .requestMatchers("/mya/back-office", "/mya/back-office/error")
                .permitAll() //backoffice
                .requestMatchers(HttpMethod.POST, "/mya/auth/confirm", "/mya/auth/email").permitAll()
//            .requestMatchers("/mya/backoffice/**").permitAll() // role인지파악
                .requestMatchers(HttpMethod.GET, "/mya/view/**").permitAll() // html 허용
                .requestMatchers(HttpMethod.GET, "/mya/back-office/**").permitAll()
                .requestMatchers("/mya/chats/**", "/mya-websocket").permitAll()
                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
    );

    // 필터 관리
    http.addFilterAfter(jwtAuthorizationFilter(),
        UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}