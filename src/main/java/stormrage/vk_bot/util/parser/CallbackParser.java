package stormrage.vk_bot.util.parser;

import stormrage.vk_bot.dto.Callback;
import stormrage.vk_bot.entity.MessageIn;
import java.util.Map;

/**
 * This class is needed for parsing from {@link Callback} to {@link
 * MessageIn}.
 */
public class CallbackParser {

  private CallbackParser() {
  }

  public static MessageIn fromCallbackToMessageIn(Callback callback) {
    Map<String, Object> messageMap = callback.getMessageMap();
    return MessageIn.builder()
        .id(Long.parseLong(String.valueOf(messageMap.get("id"))))
        .date(Long.parseLong(String.valueOf(messageMap.get("date"))))
        .peerId(Long.parseLong(String.valueOf(messageMap.get("peer_id"))))
        .fromId(Long.parseLong(String.valueOf(messageMap.get("from_id"))))
        .groupId(callback.getGroupId())
        .text(String.valueOf(messageMap.get("text")))
        .attachments(String.valueOf(messageMap.get("attachments")))
        .replyMessage(messageMap.get("reply_message") != null ? String.valueOf(
            messageMap.get("reply_message")) : null)
        .build();
  }
}
