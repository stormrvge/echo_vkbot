package com.example.vk_bot.service;

import static com.example.vk_bot.util.parser.AttachmentParser.parseStickerId;
import static com.example.vk_bot.util.parser.AttachmentParser.parseAttachments;
import static com.example.vk_bot.util.parser.AttachmentParser.parseReplyMessageId;
import static com.example.vk_bot.util.parser.AttachmentParser.isSticker;

import com.example.vk_bot.dto.MessageOut;
import com.example.vk_bot.util.parser.VkApiProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * This class is needed for creating {@link URI} to send response to VK API.
 */
@Component
@RequiredArgsConstructor
public class ResponseURICreator {

  private final ObjectMapper objectMapper;
  private final VkApiProperties vkApiProperties;

  public URI createUri(MessageOut messageOut) {
    if (isSticker(messageOut.getAttachment())) {
      return createSendStickerURI(messageOut);
    } else {
      return createSendMessageURI(messageOut);
    }
  }

  private URI createSendMessageURI(MessageOut messageOut) {
    messageOut.setAttachment(parseAttachments(messageOut.getAttachment()));
    if (messageOut.getReplyMessage() != null) {
      messageOut.setReplyTo(Long.parseLong(parseReplyMessageId(messageOut.getReplyMessage())));
    }
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
            "https://api.vk.com/method/messages.send")
        .queryParams(convertMessageOutIntoMap(messageOut))
        .queryParam("access_token", vkApiProperties.getAccessToken())
        .queryParam("v", vkApiProperties.getVersion());
    return uriBuilder.build().toUri();
  }

  private URI createSendStickerURI(MessageOut messageOut) {
    String stickerId = parseStickerId(messageOut.getAttachment());
    return UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/messages.send")
        .queryParams(convertMessageOutIntoMap(messageOut))
        .queryParam("sticker_id", stickerId)
        .queryParam("access_token", vkApiProperties.getAccessToken())
        .queryParam("v", vkApiProperties.getVersion())
        .build()
        .toUri();
  }

  /**
   * This method converts {@link MessageOut} to {@link MultiValueMap}. This is needed for sending
   * parameters to {@link URI}.
   */
  private MultiValueMap<String, String> convertMessageOutIntoMap(MessageOut messageOut) {
    MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
    Map<String, String> fieldMap = objectMapper.convertValue(messageOut,
        new TypeReference<Map<String, String>>() {
        });
    fieldMap.forEach((k, v) -> {
      if (v != null) {
        valueMap.add(k, v);
      }
    });
    return valueMap;
  }
}
