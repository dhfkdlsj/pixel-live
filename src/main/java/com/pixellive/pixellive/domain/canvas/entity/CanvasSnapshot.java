package com.pixellive.pixellive.domain.canvas.entity;

import com.pixellive.pixellive.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "canvas_snapshot")
public class CanvasSnapshot extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long canvasId;

  @Lob
  @Column(columnDefinition = "LONGTEXT", nullable = false)
  private String pixelDataJson; // 전체 픽셀 데이터를 JSON 문자열로 저장하기

  @Builder
  public CanvasSnapshot(Long canvasId, String pixelDataJson) {
    this.canvasId = canvasId;
    this.pixelDataJson = pixelDataJson;
  }
}
