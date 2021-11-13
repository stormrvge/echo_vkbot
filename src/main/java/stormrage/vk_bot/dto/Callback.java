package stormrage.vk_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * This is class will be converted from VK API request JSON via {@link
 * org.springframework.web.bind.annotation.RequestBody}.
 */
@Getter
@Setter
public class Callback {

  private CallbackType type;
  private Map<String, Object> messageMap;
  @JsonProperty(value = "group_id")
  private long groupId;
  private String secret;
  @JsonProperty(value = "event_id")
  private String eventId;
  @JsonProperty(value = "reply_message")
  private String replyMessage;

  @SuppressWarnings(value = "unchecked")
  @JsonProperty(value = "object")
  private void unpackMessageFromObject(Map<String, Object> objectMap) {
    messageMap = (Map<String, Object>) Objects.requireNonNull(
        objectMap.get("message"));
  }

  public enum CallbackType {
    @JsonProperty("message_new") MESSAGE_NEW,
    @JsonProperty("confirmation") CONFIRMATION
  }
}
