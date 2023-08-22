package com.example.meongnyangbook.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


  @Bean
  public OpenAPI openAPI() {

    Info info = new Info()
        .version("v1.0.0")
        .title("MeongNyang")
        .description("MeognNyangBook swagger");

    // API 요청헤더에 인증정보 포함
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

    // SecuritySchemes 등록
    Components auth = new Components()
        .addSecuritySchemes("Authorization", new SecurityScheme()
            .name("Authorization")
            .type(SecurityScheme.Type.HTTP) // HTTP 방식
            .scheme("bearer")
            .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

    return new OpenAPI()
        .info(info)
        .addSecurityItem(securityRequirement)
        .components(auth);

  }

}