package com.example.vk_bot.util.parser;

import com.example.vk_bot.dto.Callback;
import com.example.vk_bot.entity.MessageIn;
import java.util.Map;

/**
 * This class is needed for parsing from {@link com.example.vk_bot.dto.Callback} to {@link
 * com.example.vk_bot.entity.MessageIn}.
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
