package stormrage.vk_bot.service;

import stormrage.vk_bot.dto.MessageOut;

/**
 * This interface is responsible for sending {@link MessageOut} to VK API.
 */
public interface MessageService {

  void sendMessage(MessageOut messageOut);
}
