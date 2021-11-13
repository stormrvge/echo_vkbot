package stormrage.vk_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * This is class will be converted from answer on request to VK API.
 */
@Getter
@Setter
public class MessageAnswer {

  @JsonProperty(value = "peer_id")
  private long peerId;
  @JsonProperty(value = "message_id")
  private long messageId;
  @JsonProperty(value = "error_code")
  private long errorCode;
  @JsonProperty(value = "error_msg")
  private String errorMsg;
}
