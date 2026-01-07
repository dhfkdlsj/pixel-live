package com.pixellive.pixellive.domain.canvas.controller;

import com.pixellive.pixellive.domain.canvas.Service.CanvasService;
import com.pixellive.pixellive.domain.canvas.dto.CanvasResponse;

import com.pixellive.pixellive.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Canvas API", description = "캔버스 조회 및 설정 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/canvas")
public class CanvasController {

  private final CanvasService canvasService;

  @Operation(summary = "초기 캔버스 조회", description = "현재 활성화된 캔버스의 정보와 모든 픽셀 데이터를 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<CanvasResponse>> getCanvas() {
    CanvasResponse response = canvasService.getInitialCanvas();
    return ResponseEntity.ok(ApiResponse.ok(response));
  }
}
