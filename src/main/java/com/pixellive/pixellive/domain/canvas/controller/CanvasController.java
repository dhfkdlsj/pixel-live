package com.pixellive.pixellive.domain.canvas.controller;

import com.pixellive.pixellive.domain.canvas.Service.CanvasService;
import com.pixellive.pixellive.domain.canvas.dto.CanvasResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/canvas")
public class CanvasController {

  private final CanvasService canvasService;

  @GetMapping
  public ResponseEntity<CanvasResponse> getCanvas() {
    CanvasResponse response = canvasService.getInitialCanvas();
    return ResponseEntity.ok(response);
  }
}
