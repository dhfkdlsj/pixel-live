package com.pixellive.pixellive.domain.pixel.repository;

import com.pixellive.pixellive.domain.pixel.entity.Pixel;
import com.pixellive.pixellive.domain.pixel.entity.PixelId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PixelRepository extends JpaRepository<Pixel, PixelId> {
  // 특정 캔버스의 모든 픽셀 가져오기
  // TODO N*N 커지면 레디스 캐싱같은거 생각해두기 서버 올릴때 캐시워밍해두고 간간히 영속
  List<Pixel> findAllByCanvasId(Long canvasId);
}
