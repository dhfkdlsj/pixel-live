package com.pixellive.pixellive.global.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixellive.pixellive.domain.pixel.dto.PixelDto;
import com.pixellive.pixellive.domain.pixel.service.PixelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixelWebSocketHandler extends TextWebSocketHandler {

  private final PixelService pixelService;
  private final ObjectMapper objectMapper = new ObjectMapper(); // JSON -> 자바 객체 파싱용

  // 동시 접속자 세션 리스트 (Thread-Safe한 Set 사용)
  private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.add(session); // 접속자 명단에 추가
    log.info("🟢 사용자 접속: {} (총 접속자: {}명)", session.getId(), sessions.size());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();

    try {
      // 1. JSON -> Java Object 변환
      PixelDto requestDto = objectMapper.readValue(payload, PixelDto.class);
      log.info("📩 픽셀 요청: {}", requestDto);

      // 2. 서비스 로직 실행 (DB 저장)
      PixelDto responseDto = pixelService.updatePixel(requestDto);

      // 3. Java Object -> JSON 변환
      String jsonResponse = objectMapper.writeValueAsString(responseDto);

      // 4. 접속한 모든 사람에게 전송 (Broadcasting)
      broadcast(jsonResponse);

    } catch (Exception e) {
      log.error("❌ 메시지 처리 중 오류 발생: {}", e.getMessage());
      // 필요하다면 에러 메시지를 보낸 사람한테만 전송
      // session.sendMessage(new TextMessage("Error: " + e.getMessage()));
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    sessions.remove(session); // 명단에서 제거
    log.info("🔴 사용자 종료: {} (남은 접속자: {}명)", session.getId(), sessions.size());
  }

  // 모든 세션에 메시지 뿌리기
  private void broadcast(String message) {
    sessions.parallelStream().forEach(session -> {
      try {
        if (session.isOpen()) {
          session.sendMessage(new TextMessage(message));
        }
      } catch (IOException e) {
        log.error("전송 실패: {}", session.getId(), e);
      }
    });
  }
}
