package com.pixellive.pixellive.global.config;

import com.pixellive.pixellive.global.websocket.PixelWebSocketHandler;
import com.pixellive.pixellive.global.websocket.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private final PixelWebSocketHandler pixelWebSocketHandler;
  private final WebSocketHandshakeInterceptor handshakeInterceptor;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(pixelWebSocketHandler, "/ws/pixel")
        .addInterceptors(handshakeInterceptor) // IP 인터셉터 등록
        .setAllowedOrigins("*"); // 개발용: 모든 도메인에서 접속 허용 (CORS 해제)
  }
}
