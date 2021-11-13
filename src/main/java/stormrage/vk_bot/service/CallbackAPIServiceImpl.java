package stormrage.vk_bot.service;

import java.security.InvalidParameterException;
import stormrage.vk_bot.dto.Callback;
import stormrage.vk_bot.dto.MessageOut;
import stormrage.vk_bot.entity.MessageIn;
import stormrage.vk_bot.repository.MessageInRepository;
import stormrage.vk_bot.util.exceptions.InvalidSecretException;
import stormrage.vk_bot.util.exceptions.UnsupportedTypeOfCallback;
import stormrage.vk_bot.util.parser.VkApiProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import stormrage.vk_bot.util.parser.CallbackParser;

/**
 * Implementation of {@link CallbackAPIService}.
 */
@Service
@RequiredArgsConstructor
public class CallbackAPIServiceImpl implements CallbackAPIService {

  private static final Logger log = LoggerFactory.getLogger(CallbackAPIServiceImpl.class);

  private final MessageService messageService;
  private final MessageInRepository messageInRepository;
  private final VkApiProperties vkApiProperties;

  public String handleCallback(Callback callback) {
    if (callback.isEmpty()) {
      log.info("An empty callback came to the server");
      throw new InvalidParameterException("An empty callback came to the server.");
    }
    validateCallbackSecret(callback.getSecret());
    switch (callback.getType()) {
      case MESSAGE_NEW:
        handleIncomingMessage(CallbackParser.fromCallbackToMessageIn(callback));
        return "ok";
      case CONFIRMATION:
        return vkApiProperties.getConfirmation();
      default:
        log.info("An unsupported request type came in.");
        throw new UnsupportedTypeOfCallback("Service doesn't support this type of callback.");
    }
  }

  private void validateCallbackSecret(String callbackSecret) {
    if (!vkApiProperties.getSecret().equals(callbackSecret)) {
      log.info("A request came with an invalid secret key: {}", callbackSecret);
      throw new InvalidSecretException("Callback secret is invalid.");
    }
  }

  private void handleIncomingMessage(MessageIn messageIn) {
    MessageIn saved = messageInRepository.save(messageIn);
    MessageOut messageOut = MessageOut.builder()
        .peerId(saved.getPeerId())
        .message("You said: " + saved.getText())
        .groupId(saved.getGroupId())
        .attachment(messageIn.getAttachments())
        .replyMessage(messageIn.getReplyMessage())
        .build();
    messageService.sendMessage(messageOut);
  }
}
