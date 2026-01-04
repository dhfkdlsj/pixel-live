package com.pixellive.pixellive.domain.pixel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pixel_history", indexes = {
    @Index(name = "idx_hist_canvas_time", columnList = "canvasId, createdAt"), // 타임랩스 조회용
    @Index(name = "idx_hist_coordinate", columnList = "x, y") // 히트맵 조회용
})
public class PixelHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long canvasId;

  @Column(nullable = false)
  private int x;

  @Column(nullable = false)
  private int y;

  @Column(length = 7, nullable = false)
  private String color;

  @Column(length = 64, nullable = false)
  private String userIdentifier;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt; // BaseTimeEntity 상속 안 함

  @Builder
  public PixelHistory(Long canvasId, int x, int y, String color, String userIdentifier) {
    this.canvasId = canvasId;
    this.x = x;
    this.y = y;
    this.color = color;
    this.userIdentifier = userIdentifier;
  }
}
