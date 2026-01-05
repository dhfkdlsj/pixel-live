package com.pixellive.pixellive.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("PixelLive API Document")
            .description("실시간 픽셀 공유 서비스 PixelLive의 API 명세서입니다.")
            .version("v1.0.0"));
  }
}
