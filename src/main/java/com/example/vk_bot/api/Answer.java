package com.example.vk_bot.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Answer {


  @PostMapping("/")
  public ResponseEntity<String> resp() {
    return ResponseEntity.ok().body("3df98625");
  }
}
