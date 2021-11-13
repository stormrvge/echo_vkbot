package stormrage.vk_bot.service;

import stormrage.vk_bot.dto.Callback;
import stormrage.vk_bot.controller.EchoController;

/**
 * This interface is responsible for handle {@link Callback} and return answer to {@link
 * EchoController}.
 */
public interface CallbackAPIService {

  String handleCallback(Callback callback);
}
