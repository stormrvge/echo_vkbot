package com.example.vk_bot.service;

import com.example.vk_bot.dto.MessageOut;

/**
 * This interface is responsible for sending {@link MessageOut} to VK API.
 */
public interface MessageService {

  void sendMessage(MessageOut messageOut);
}
