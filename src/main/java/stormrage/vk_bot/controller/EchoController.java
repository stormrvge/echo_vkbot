package stormrage.vk_bot.controller;

import stormrage.vk_bot.dto.Callback;
import stormrage.vk_bot.service.CallbackAPIService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger log = LoggerFactory.getLogger(EchoController.class);

  private final CallbackAPIService callbackAPIService;

  @PostMapping("/")
  public ResponseEntity<String> resp(@RequestBody Callback callback) {
    log.info("A new message has been sent to the bot.");
    return ResponseEntity.ok(callbackAPIService.handleCallback(callback));
  }
}
