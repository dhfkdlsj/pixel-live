package com.pixellive.pixellive.global.config;

import com.pixellive.pixellive.domain.canvas.entity.Canvas;
import com.pixellive.pixellive.domain.canvas.repository.CanvasRepository;
import com.pixellive.pixellive.domain.pixel.entity.Pixel;
import com.pixellive.pixellive.domain.pixel.repository.PixelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final CanvasRepository canvasRepository;
  private final PixelRepository pixelRepository;

  @Override
  public void run(String... args) throws Exception {
    // 이미 캔버스가 있다면 초기화하지 않음
    if (canvasRepository.count() > 0) return;

    // 1. 캔버스 생성 (30x30 크기)
    Canvas canvas = Canvas.builder()
        .title("Pixel Live Season 1")
        .width(30)
        .height(30)
        .build(); // isActive=true 기본값

    Canvas savedCanvas = canvasRepository.save(canvas);

    // 2. 테스트용 픽셀 몇 개 찍기
    pixelRepository.save(Pixel.builder()
        .canvasId(savedCanvas.getId())
        .x(10).y(10).color("#FF0000").updatedBy("System")
        .build());

    pixelRepository.save(Pixel.builder()
        .canvasId(savedCanvas.getId())
        .x(15).y(15).color("#0000FF").updatedBy("System")
        .build());

    System.out.println("✅ 테스트용 캔버스 및 픽셀 데이터 초기화 완료!");
  }
}
