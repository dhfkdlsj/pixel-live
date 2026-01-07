package com.pixellive.pixellive.domain.canvas.Service;

import com.pixellive.pixellive.domain.canvas.dto.CanvasResponse;
import com.pixellive.pixellive.domain.canvas.entity.Canvas;
import com.pixellive.pixellive.domain.canvas.repository.CanvasRepository;
import com.pixellive.pixellive.domain.pixel.entity.Pixel;
import com.pixellive.pixellive.domain.pixel.repository.PixelRepository;
import com.pixellive.pixellive.global.error.CustomException;
import com.pixellive.pixellive.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CanvasService {

  private final CanvasRepository canvasRepository;
  private final PixelRepository pixelRepository;

  public CanvasResponse getInitialCanvas() {
    // 1. 활성화된 캔버스 찾기
    Canvas canvas = canvasRepository.findFirstByIsActiveTrue()
        .orElseThrow(() -> new CustomException(ErrorCode.CANVAS_NOT_FOUND));

    // 2. 해당 캔버스의 모든 픽셀 데이터 조회
    List<Pixel> pixels = pixelRepository.findAllByCanvasId(canvas.getId());

    // 3. DTO로 변환하여 반환
    return CanvasResponse.of(canvas, pixels);
  }
}
