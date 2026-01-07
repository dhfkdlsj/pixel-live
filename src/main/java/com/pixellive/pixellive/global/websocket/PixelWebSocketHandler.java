package com.pixellive.pixellive.global.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class PixelWebSocketHandler extends TextWebSocketHandler {

  // 1. 소켓 연결 생성 시 실행
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("🟢 소켓 연결 성공: {}", session.getId());
  }

  // 2. 메시지 수신 시 실행
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("📩 수신 메시지: {}", payload);

    // 테스트용: 받은 메시지를 그대로 돌려주기 (Echo)
    session.sendMessage(new TextMessage("서버 응답: " + payload));
  }

  // 3. 소켓 연결 종료 시 실행
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("🔴 소켓 연결 종료: {}", session.getId());
  }
}
