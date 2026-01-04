package com.pixellive.pixellive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PixelLiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(PixelLiveApplication.class, args);
  }

}
