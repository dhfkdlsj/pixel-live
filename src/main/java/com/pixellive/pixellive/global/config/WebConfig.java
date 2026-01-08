package com.pixellive.pixellive.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 1. 모든 경로에 대해
        .allowedOrigins("http://localhost:5173") // 2. 이 출처(프론트엔드)만 허용 (보안상 '*'보다 좋음)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 3. 허용할 HTTP 메서드
        .allowedHeaders("*") // 4. 모든 헤더 허용
        .allowCredentials(true) // 5. 쿠키/인증 정보 포함 허용
        .maxAge(3600); // 6. 브라우저가 이 설정을 1시간동안 기억 (매번 안 물어봄)
  }
}
