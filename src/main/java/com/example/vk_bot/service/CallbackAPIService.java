package com.example.vk_bot.service;

import com.example.vk_bot.dto.Callback;

/**
 * This interface is responsible for handle {@link Callback} and return answer to {@link
 * com.example.vk_bot.controller.EchoController}.
 */
public interface CallbackAPIService {

  String handleCallback(Callback callback);
}
