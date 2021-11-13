package com.example.vk_bot.service;

import com.example.vk_bot.dto.MessageOut;
import com.example.vk_bot.dto.MessageAnswer;
import com.example.vk_bot.util.validator.MessageSendResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link MessageService}.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final RestTemplate restTemplate = new RestTemplate();
  private final ResponseURICreator responseURICreator;
  private final MessageSendResponse messageSendResponse;

  public void sendMessage(MessageOut messageOut) {
    sendToServer(messageOut);
  }

  private void sendToServer(MessageOut messageOut) {
    messageOut.setRandomId((long) messageOut.hashCode());
    URI uri = responseURICreator.createUri(messageOut);
    ResponseEntity<MessageAnswer> resp = restTemplate.postForEntity(uri, null, MessageAnswer.class);
    messageSendResponse.validate(resp);
  }
}
