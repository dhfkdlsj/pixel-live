package com.pixellive.pixellive.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

  private final boolean success;
  private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL) // data가 null이면 JSON에서 제외
  private final T data;

  // 성공 응답 (데이터 있는거)
  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", data);
  }

  // 성공 응답 (데이터 없는거)
  public static <T> ApiResponse<T> ok() {
    return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", null);
  }

  // 실패 응답
  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>(false, message, null);
  }

  private ApiResponse(boolean success, String message, T data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }
}
