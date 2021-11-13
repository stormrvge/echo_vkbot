package com.example.vk_bot.controller;

import com.example.vk_bot.dto.Callback;
import com.example.vk_bot.service.CallbackAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that accepts the request from VK API and forward it into {@link CallbackAPIService}.
 */
@RestController
@RequiredArgsConstructor
public class EchoController {

  private final CallbackAPIService callbackAPIService;

  @PostMapping("/")
  public ResponseEntity<String> resp(@RequestBody Callback callback) {
    return ResponseEntity.ok(callbackAPIService.handleCallback(callback));
  }
}
