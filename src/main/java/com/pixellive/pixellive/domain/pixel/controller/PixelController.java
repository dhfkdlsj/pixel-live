package com.pixellive.pixellive.domain.pixel.controller;

import com.pixellive.pixellive.domain.pixel.dto.PixelDto;
import com.pixellive.pixellive.domain.pixel.service.PixelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pixels")
public class PixelController {

  private final PixelService pixelService;

  // 전체 픽셀 조회
  @GetMapping
  public List<PixelDto> getAllPixels() {
    return pixelService.getAllPixels();
  }
}
