package stormrage.vk_bot.util.parser;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * This class contains properties from vk_api.properties file.
 */
@Component
@Data
@PropertySource(value = "classpath:vk_api.properties")
@ConfigurationProperties(prefix = "vk.api")
public class VkApiProperties {

  @NotEmpty
  private String accessToken;
  @NotEmpty
  private String secret;
  @NotEmpty
  private String confirmation;
  @NotEmpty
  private Double version;
}
