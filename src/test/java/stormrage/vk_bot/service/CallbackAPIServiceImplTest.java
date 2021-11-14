package stormrage.vk_bot.service;

import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.web.client.RestTemplate;
import stormrage.vk_bot.dto.Callback;
import stormrage.vk_bot.repository.MessageInRepository;
import stormrage.vk_bot.util.exceptions.InvalidSecretException;
import stormrage.vk_bot.util.parser.VkApiProperties;
import stormrage.vk_bot.util.validator.MessageSendResponse;

@TestInstance(Lifecycle.PER_CLASS)
class CallbackAPIServiceImplTest {

  private CallbackAPIServiceImpl callbackAPIService;
  private VkApiProperties vkApiProperties;

  @BeforeAll
  public void init() {
    vkApiProperties = new VkApiProperties();
    vkApiProperties.setAccessToken("access_token");
    vkApiProperties.setVersion(1.0);

    MessageServiceImpl messageService = new MessageServiceImpl(new RestTemplate(),
        new ResponseURICreator(new ObjectMapper(), mock(
            VkApiProperties.class)), new MessageSendResponse());
    callbackAPIService = new CallbackAPIServiceImpl(messageService, mock(MessageInRepository.class),
        vkApiProperties);
  }

  @Test
  void handleCallbackWithEmptyCallback() {
    Callback emptyCallback = new Callback();
    Assertions.assertThrows(InvalidParameterException.class,
        () -> callbackAPIService.handleCallback(emptyCallback));
  }

  @Test
  void handleCallbackWithInvalidJson() {
    Assertions.assertThrows(NullPointerException.class,
        () -> callbackAPIService.handleCallback(null));
  }

  @Test
  void handleCallbackWithInvalidSecret() {
    Callback invalidSecretCallback = new Callback();
    vkApiProperties.setSecret("token");
    invalidSecretCallback.setSecret("invalid_token");

    Assertions.assertThrows(InvalidSecretException.class,
        () -> callbackAPIService.handleCallback(invalidSecretCallback));
  }

  @Test
  void handleCallbackWithUnsupportedType() {
    Callback unsupportedTypeCallback = new Callback();
    unsupportedTypeCallback.setSecret("valid");
    unsupportedTypeCallback.setType(null);
    vkApiProperties.setSecret("valid");
    Assertions.assertThrows(NullPointerException.class,
        () -> callbackAPIService.handleCallback(unsupportedTypeCallback));
  }
}
