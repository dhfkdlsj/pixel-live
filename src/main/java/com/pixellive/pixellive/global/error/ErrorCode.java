package com.pixellive.pixellive.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  // Common (공통 에러)
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

  // Domain Specific (도메인 에러)
  CANVAS_NOT_FOUND(HttpStatus.NOT_FOUND, "활성화된 캔버스를 찾을 수 없습니다."),
  PIXEL_NOT_FOUND(HttpStatus.NOT_FOUND, "픽셀 정보를 찾을 수 없습니다.");

  private final HttpStatus status;
  private final String message;
}
