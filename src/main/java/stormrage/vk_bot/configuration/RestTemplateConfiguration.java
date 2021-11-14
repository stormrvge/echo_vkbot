package stormrage.vk_bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class that creates instance of {@link RestTemplate}.
 */
@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate createRestTemplateInstance() {
    return new RestTemplate();
  }
}
