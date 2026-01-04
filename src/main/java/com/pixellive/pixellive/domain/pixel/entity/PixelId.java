package com.pixellive.pixellive.domain.pixel.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode // 복합키 식별용
public class PixelId implements Serializable {
  private int x;
  private int y;
}
