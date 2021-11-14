package stormrage.vk_bot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import stormrage.vk_bot.dto.MessageOut;
import stormrage.vk_bot.util.parser.VkApiProperties;


@TestInstance(Lifecycle.PER_CLASS)
class ResponseURICreatorTest {

  private ResponseURICreator responseURICreator;

  @BeforeAll
  public void init() {
    VkApiProperties vkApiProperties = new VkApiProperties();
    vkApiProperties.setAccessToken("access_token");
    vkApiProperties.setVersion(1.0);

    responseURICreator = new ResponseURICreator(new ObjectMapper(), vkApiProperties);
  }

  @Test
  void createUriWithParams() {
    String expectedURI = "https://api.vk.com/method/messages.send?message=hello&attachment=&user_id=1337&group_id=42&access_token=access_token&v=1.0";
    MessageOut messageOut = MessageOut.builder()
        .userId(1337L)
        .groupId(42L)
        .message("hello")
        .attachment("[]")
        .build();
    URI actualURI = responseURICreator.createUri(messageOut);
    assertEquals(expectedURI, actualURI.toString());
  }

  @Test
  void createUriWithPhotoAttachment() {
    String expectedURI =
        "https://api.vk.com/method/messages.send?attachment=photo686258714_457239164_f37abfb7b0ced25c60,"
            + "photo686258714_457239165_1fde3f9a9b0ddb9f68&user_id=1337&group_id=42&access_token=access_token&v=1.0";
    MessageOut messageOut = MessageOut.builder()
        .userId(1337L)
        .groupId(42L)
        .attachment(
            "[{type=photo, photo={album_id=-3, date=1636809846, id=457239164, owner_id=686258714, "
                + "has_tags=false, access_key=f37abfb7b0ced25c60, sizes=[{height=75, "
                + "url=https://sun9-24.userapi.com/impg/dlYhj793x_kiga6dfr8SPXSpoJZ0j4tVjjL8IQ/yDsggToYylM.jpg"
                + "?size=75x75&quality=96&sign=145e13d28024d63be03ebd8780a1f7e1&c_uniq_tag=P4iEFc2VoF5dTykSVEQXG"
                + "88d_mpJbdC5QnFPdWyelR0&type=album, type=s, width=75}, {height=128, "
                + "url=https://sun9-24.userapi.com/impg/dlYhj793x_kiga6dfr8SPXSpoJZ0j4tVjjL8IQ/yDsggToYylM.jpg?size=128x128"
                + "&quality=96&sign=5253990e519e3f0a8b90576a095ed047&c_uniq_tag=K2mRYM_RYcuWnyU8ZcjIgf4HD7_KDY9Bbp10iYkw7BM&type=album, "
                + "type=m, width=128}], text=}}, {type=photo, photo={album_id=-3, date=1636809846, id=457239165, owner_id=686258714, has_tags=false, "
                + "access_key=1fde3f9a9b0ddb9f68, sizes=[{height=75, url=https://sun9-24.userapi.com/impg/dlYhj793x_kiga6dfr8SPXSpoJZ0j4tVjjL8IQ/"
                + "yDsggToYylM.jpg?size=75x75&quality=96&sign=145e13d28024d63be03ebd8780a1f7e1&c_uniq_tag=P4iEFc2VoF5dTykSVEQXG88d_mpJbdC5QnFPdWyelR0&"
                + "type=album, type=s, width=75}], text=}}]")
        .build();
    URI actualURI = responseURICreator.createUri(messageOut);
    assertEquals(expectedURI, actualURI.toString());
  }

  @Test
  void createUriWithStickerId() {
    String expectedURI = "https://api.vk.com/method/messages.send?user_id=1337&group_id=42&sticker_id=9009&access_token=access_token&v=1.0";
    MessageOut messageOut = MessageOut.builder()
        .userId(1337L)
        .groupId(42L)
        .attachment(
            "[{type=sticker, sticker={product_id=279, sticker_id=9009, "
                + "images=[{url=https://vk.com/sticker/1-9009-64, width=64, height=64}, "
                + "{url=https://vk.com/sticker/1-9009-128, width=128, height=128}, "
                + "{url=https://vk.com/sticker/1-9009-256, width=256, height=256}, "
                + "{url=https://vk.com/sticker/1-9009-352, width=352, height=352}, "
                + "{url=https://vk.com/sticker/1-9009-512, width=512, height=512}], "
                + "images_with_background=[{url=https://vk.com/sticker/1-9009-64b, width=64, height=64}, "
                + "{url=https://vk.com/sticker/1-9009-128b, width=128, height=128}, "
                + "{url=https://vk.com/sticker/1-9009-256b, width=256, height=256}, "
                + "{url=https://vk.com/sticker/1-9009-352b, width=352, height=352}, "
                + "{url=https://vk.com/sticker/1-9009-512b, width=512, height=512}]}}]")
        .build();
    URI actualURI = responseURICreator.createUri(messageOut);
    assertEquals(expectedURI, actualURI.toString());
  }
}
