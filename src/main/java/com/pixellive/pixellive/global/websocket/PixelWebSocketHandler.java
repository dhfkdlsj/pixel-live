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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixelWebSocketHandler extends TextWebSocketHandler {

  private final PixelService pixelService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  // 접속자 관리용 세션 저장소
  private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

  // ✅ 연결 성공 시 (입장)
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.add(session);
    log.info("🟢 사용자 입장! 세션 ID: {}, 현재 접속자: {}", session.getId(), sessions.size());

    // 전체에게 "현재 인원수" 방송
    broadcastUserCount();
  }

  // ✅ 메시지 수신 시
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();

    try {
      // 1. JSON -> DTO 변환
      PixelDto requestDto = objectMapper.readValue(payload, PixelDto.class);

      // 2. 서비스 로직 실행 (DB 저장)
      PixelDto responseDto = pixelService.updatePixel(requestDto);

      // 3. DTO -> JSON 변환
      String jsonResponse = objectMapper.writeValueAsString(responseDto);

      // 4. 접속한 모든 사람에게 전송 (broadcast 메서드 활용)
      broadcast(jsonResponse);

    } catch (Exception e) {
      log.error("❌ 메시지 처리 중 오류 발생: {}", e.getMessage());
    }
  }

  // ✅ 연결 종료 시 (퇴장)
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    sessions.remove(session);
    log.info("🔴 사용자 퇴장! 세션 ID: {}, 현재 접속자: {}", session.getId(), sessions.size());

    // 전체에게 "현재 인원수" 방송
    broadcastUserCount();
  }

  // 📢 전체 메시지 전송 헬퍼 메서드
  private void broadcast(String message) {
    TextMessage textMessage = new TextMessage(message);

    for (WebSocketSession s : sessions) {
      if (s.isOpen()) {
        try {
          // 🔒 동기화: 한 세션에 대해 한 번에 하나의 스레드만 메시지를 보내도록 잠금
          synchronized (s) {
            s.sendMessage(textMessage);
          }
        } catch (IOException e) {
          log.error("전송 실패: {}", s.getId(), e);
          // 전송 실패한 세션은 닫혀있을 가능성이 높으므로 제거 시도 등 추가 처리가 가능하지만,
          // 보통 afterConnectionClosed에서 처리되므로 로그만 남김
        }
      }
    }
  }

  // 🔥 접속자 수 알림 메서드 (Protocol: type="USER_COUNT")
  private void broadcastUserCount() {
    try {
      // 프론트가 구분할 수 있게 type 필드 추가
      String countMessage = objectMapper.writeValueAsString(Map.of(
          "type", "USER_COUNT",
          "count", sessions.size()
      ));

      // 위에서 만든 broadcast 재사용
      broadcast(countMessage);

    } catch (IOException e) {
      log.error("인원수 방송 실패", e);
    }
  }
}
