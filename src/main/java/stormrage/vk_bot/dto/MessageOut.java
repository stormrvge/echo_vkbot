package stormrage.vk_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is sent as a response to the VK API.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = {"randomId", "replyMessage"})
public class MessageOut {

  @JsonProperty(value = "user_id")
  private Long userId;
  @JsonProperty(value = "random_id")
  private Long randomId;
  @JsonProperty(value = "peer_id")
  private Long peerId;
  private String message;
  private String attachment;
  @JsonProperty(value = "reply_to")
  private Long replyTo;
  @JsonProperty(value = "forward_messages")
  private String forwardMessages;
  @JsonProperty(value = "sticker_id")
  private Long stickerId;
  @JsonProperty(value = "group_id")
  private Long groupId;
  @JsonIgnore
  private String replyMessage;
}
