package stormrage.vk_bot.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stormrage.vk_bot.dto.Callback;

/**
 * This class is entity that will be saved in database. After response, we will parse from {@link
 * Callback} to {@link MessageIn} and save to database.
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageIn implements Serializable {

  @Id
  private Long id;
  private Long date;
  private Long peerId;
  private Long fromId;
  @Column(length = 9000)  // Maximal length of text what we can send is 9000 symbols.
  private String text;
  private Long groupId;
  @Transient
  private String attachments;
  @Transient
  private String replyMessage;
}
