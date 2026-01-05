package com.pixellive.pixellive.domain.canvas.dto;

import com.pixellive.pixellive.domain.canvas.entity.Canvas;
import com.pixellive.pixellive.domain.pixel.dto.PixelDto;
import com.pixellive.pixellive.domain.pixel.entity.Pixel;
import java.util.List;
import java.util.stream.Collectors;

public record CanvasResponse(
    Long canvasId,
    String title,
    int width,
    int height,
    List<PixelDto> pixels
) {
  public static CanvasResponse of(Canvas canvas, List<Pixel> pixels) {
    List<PixelDto> pixelDtos = pixels.stream()
        .map(PixelDto::from)
        .collect(Collectors.toList());

    return new CanvasResponse(
        canvas.getId(),
        canvas.getTitle(),
        canvas.getWidth(),
        canvas.getHeight(),
        pixelDtos
    );
  }
}
