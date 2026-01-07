package com.pixellive.pixellive.domain.pixel.service;

import com.pixellive.pixellive.domain.pixel.dto.PixelDto;
import com.pixellive.pixellive.domain.pixel.entity.Pixel;
import com.pixellive.pixellive.domain.pixel.repository.PixelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PixelService {

  private final PixelRepository pixelRepository;

  @Transactional
  public PixelDto updatePixel(PixelDto requestDto) {
    // 1. 현재 활성 캔버스 ID (TODO 일단 1번으로 하드코딩, 나중에 동적으로 변경)
    Long currentCanvasId = 1L;

    // 2. DB에 저장 (이미 있으면 덮어쓰기/Update, 없으면 생성/Insert)
    Pixel pixel = Pixel.builder()
        .canvasId(currentCanvasId)
        .x(requestDto.x())
        .y(requestDto.y())
        .color(requestDto.color())
        .updatedBy("User") // 나중에 사용자 ID로 변경
        .build();

    Pixel savedPixel = pixelRepository.save(pixel);

    // 3. 저장된 데이터를 DTO로 변환해서 반환
    return PixelDto.from(savedPixel);
  }
}
