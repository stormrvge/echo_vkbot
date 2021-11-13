package com.example.vk_bot.service;

import static com.example.vk_bot.util.parser.CallbackParser.fromCallbackToMessageIn;

import com.example.vk_bot.dto.Callback;
import com.example.vk_bot.dto.MessageOut;
import com.example.vk_bot.entity.MessageIn;
import com.example.vk_bot.repository.MessageInRepository;
import com.example.vk_bot.util.exceptions.InvalidSecretException;
import com.example.vk_bot.util.exceptions.UnsupportedTypeOfCallback;
import com.example.vk_bot.util.parser.VkApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link CallbackAPIService}.
 */
@Service
@RequiredArgsConstructor
public class CallbackAPIServiceImpl implements CallbackAPIService {

  private final MessageService messageService;
  private final MessageInRepository messageInRepository;
  private final VkApiProperties vkApiProperties;

  public String handleCallback(Callback callback) {
    validateCallbackSecret(callback.getSecret());
    switch (callback.getType()) {
      case MESSAGE_NEW:
        handleIncomingMessage(fromCallbackToMessageIn(callback));
        return "ok";
      case CONFIRMATION:
        return vkApiProperties.getConfirmation();
      default:
        throw new UnsupportedTypeOfCallback("Service doesn't support this type of callback!");
    }
  }

  private void validateCallbackSecret(String callbackSecret) {
    if (!vkApiProperties.getSecret().equals(callbackSecret)) {
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
