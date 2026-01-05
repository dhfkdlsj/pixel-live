package com.pixellive.pixellive.domain.pixel.dto;

import com.pixellive.pixellive.domain.pixel.entity.Pixel;

public record PixelDto(
    int x,
    int y,
    String color
) {
  public static PixelDto from(Pixel pixel) {
    return new PixelDto(
        pixel.getId().getX(),
        pixel.getId().getY(),
        pixel.getColor()
    );
  }
}
