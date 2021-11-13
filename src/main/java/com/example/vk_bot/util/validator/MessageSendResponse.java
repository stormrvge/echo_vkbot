package com.example.vk_bot.util.validator;

import com.example.vk_bot.dto.MessageAnswer;
import com.example.vk_bot.util.exceptions.InvalidRequestException;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * This is validator class for response from VK API.
 */
@Component
public class MessageSendResponse {

  public void validate(ResponseEntity<MessageAnswer> response) {
    long errorCode = Objects.requireNonNull(response.getBody()).getErrorCode();
    if (errorCode != 0) {
      MessageAnswer answerBody = Objects.requireNonNull(response.getBody());
      String errorMsg = Objects.requireNonNull(answerBody.getErrorMsg());
      throw new InvalidRequestException(errorMsg);
    }
  }
}
