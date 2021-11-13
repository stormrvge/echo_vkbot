package stormrage.vk_bot.util.validator;

import stormrage.vk_bot.dto.MessageAnswer;
import stormrage.vk_bot.util.exceptions.InvalidRequestException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * This is validator class for response from VK API.
 */
@Component
public class MessageSendResponse {

  private static final Logger log = LoggerFactory.getLogger(MessageSendResponse.class);

  public void validate(ResponseEntity<MessageAnswer> response) {
    long errorCode = Objects.requireNonNull(response.getBody()).getErrorCode();
    if (errorCode != 0) {
      MessageAnswer answerBody = Objects.requireNonNull(response.getBody());
      String errorMsg = Objects.requireNonNull(answerBody.getErrorMsg());
      log.error("Error during validation request to VK API. Error code: {}. Message: {}", errorCode,
          errorMsg);
      throw new InvalidRequestException(errorMsg);
    }
  }
}
