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
@Table(name = "canvas")
public class Canvas extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private int width;

  @Column(nullable = false)
  private int height;

  @Column(nullable = false)
  private boolean isActive;

  @Builder
  public Canvas(String title, int width, int height) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.isActive = true;
  }
}
