package com.pixellive.pixellive.global.websocket;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

    if (request instanceof ServletServerHttpRequest) {
      HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

      // 1. 프록시(Nginx 등)를 거쳐왔는지 확인 (진짜 IP 찾기)
      String clientIp = servletRequest.getHeader("X-Forwarded-For");

      if (clientIp == null || clientIp.isEmpty()) {
        // 프록시가 없으면 그냥 원격 주소 사용
        clientIp = servletRequest.getRemoteAddr();
      } else {
        // X-Forwarded-For는 "IP1, IP2, IP3" 형태일 수 있음. 첫 번째가 진짜 IP.
        clientIp = clientIp.split(",")[0].trim();
      }

      // 2. 찾은 IP를 웹소켓 세션 속성(attributes)에 "CLIENT_IP"라는 이름으로 저장
      attributes.put("CLIENT_IP", clientIp);
    }
    return true; // true를 반환해야 연결이 진행됨
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Exception exception) {
    // 핸드쉐이크 후 처리할 것 없음
  }
}
