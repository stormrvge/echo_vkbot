package stormrage.vk_bot.service;

import static java.lang.Math.random;

import stormrage.vk_bot.dto.MessageOut;
import stormrage.vk_bot.dto.MessageAnswer;
import stormrage.vk_bot.util.validator.MessageSendResponse;
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
    messageOut.setRandomId((long) (messageOut.hashCode() + random() * 10000L));
    URI uri = responseURICreator.createUri(messageOut);
    ResponseEntity<MessageAnswer> resp = restTemplate.postForEntity(uri, null, MessageAnswer.class);
    messageSendResponse.validate(resp);
  }
}
