package com.pixellive.pixellive.domain.pixel.entity;

import com.pixellive.pixellive.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pixel_current", indexes = {
    @Index(name = "idx_pixel_canvas_id", columnList = "canvasId")
})
public class Pixel extends BaseTimeEntity {

  @EmbeddedId
  private PixelId id; // (x, y) 복합키

  @Column(nullable = false)
  private Long canvasId; // 확장성을 위한 캔버스 ID

  @Column(length = 7, nullable = false)
  private String color; // Hex Code (ex: #FF0000)

  @Column(length = 64)
  private String updatedBy; // 마지막으로 칠한 사용자 IP(Hash)

  @Builder
  public Pixel(Long canvasId, int x, int y, String color, String updatedBy) {
    this.id = new PixelId(x, y);
    this.canvasId = canvasId;
    this.color = color;
    this.updatedBy = updatedBy;
  }

  // 비즈니스 로직: 색상 변경
  public void changeColor(String newColor, String userId) {
    this.color = newColor;
    this.updatedBy = userId;
  }
}
