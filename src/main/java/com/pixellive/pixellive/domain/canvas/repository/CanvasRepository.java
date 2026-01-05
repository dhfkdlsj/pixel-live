package com.pixellive.pixellive.domain.canvas.repository;


import com.pixellive.pixellive.domain.canvas.entity.Canvas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CanvasRepository extends JpaRepository<Canvas, Long> {
  //활성화된 캔버스 찾기
  Optional<Canvas> findFirstByIsActiveTrue();
}
