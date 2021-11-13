package stormrage.vk_bot.util.parser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.util.Pair;

/**
 * This class is needed for parse attachments string presentation from VK API answer.
 */
public class AttachmentParser {

  /**
   * Tokens what we need to parse.
   */
  private static final String OWNER_ID = "owner_id";
  private static final String TYPE = "type";
  private static final String PHOTO = "photo";
  private static final String VIDEO = "video";
  private static final String AUDIO = "audio";
  private static final String DOC = "doc";
  private static final String STICKER = "sticker";
  private static final String ID = "id";
  private static final String ACCESS_KEY = "access_key";
  private static final String MEDIA_ID = "media_id";
  private static final String STICKER_ID = "sticker_id";

  private AttachmentParser() {
  }

  /**
   * This method will return a string with all parsed attachments. Example string presentation is
   * "video12345_67890,photo12345_67890".
   */
  public static String parseAttachments(String attachments) {
    ArrayList<Map<String, String>> attachmentsMap = parseInit(attachments);
    StringBuilder sb = new StringBuilder();

    for (Map<String, String> stringStringMap : attachmentsMap) {
      if (stringStringMap.get(TYPE).equals(PHOTO) || stringStringMap.get(TYPE).equals(VIDEO)
          || stringStringMap.get(TYPE).equals(AUDIO) || stringStringMap.get(TYPE).equals(DOC)
          || stringStringMap.get(TYPE).equals(STICKER)) {
        sb.append(stringStringMap.get(TYPE)).append(stringStringMap.get(OWNER_ID)).append("_")
            .append(stringStringMap.get(ID));
        if (stringStringMap.get(ACCESS_KEY) != null) {
          sb.append("_").append(stringStringMap.get(ACCESS_KEY));
        }
        sb.append(",");
      }
    }
    return sb.toString();
  }

  /**
   * This method will return sticker id.
   */
  public static String parseStickerId(String attachments) {
    ArrayList<Map<String, String>> map = parseInit(attachments);
    return map.get(0).get(STICKER_ID);
  }

  /**
   * This method will return reply message id.
   */
  public static String parseReplyMessageId(String replyMessage) {
    Matcher matcher = Pattern.compile("[A-Z_a-z-0-9]+").matcher(replyMessage);
    return createParsedJsonTokenMap(matcher).get(ID);
  }

  /**
   * This method return true if attachment is sticker, otherwise false.
   */
  public static boolean isSticker(String attachments) {
    return attachments.contains(STICKER_ID);
  }

  /**
   * This method will return list with attachments map that contains key is parsed token and value
   * of it.
   */
  private static ArrayList<Map<String, String>> parseInit(String attachments) {
    attachments = removeUnnecessaryInfo(attachments);

    ArrayList<Map<String, String>> parsedJson = new ArrayList<>();
    for (String s : splitAttachments(attachments)) {
      Matcher matcher = Pattern.compile("[A-Z_a-z-0-9]+").matcher(s);
      parsedJson.add(createParsedJsonTokenMap(matcher));
    }
    return parsedJson;
  }

  /**
   * This method will split attachments into a separate string.
   */
  private static ArrayList<String> splitAttachments(String attachments) {
    String attachmentsCopy = attachments;
    // removing strings like "?id=some_id", because it is unnecessary for parser.
    attachmentsCopy = attachmentsCopy.replaceAll("(\\?)[A-Z_a-z-0-9]+=[A-Z_a-z-0-9]+", "");
    // splitting all attachments like {attachment: 1}, {attachment: 2} by strings.
    Matcher matcher2 = Pattern.compile("\\{[^}]*+").matcher(attachmentsCopy);

    ArrayList<String> attachmentList = new ArrayList<>();
    while (matcher2.find()) {
      attachmentList.add(matcher2.group());
    }
    return attachmentList;
  }

  /**
   * This method will remove unnecessary information in nesting level > 2. This information is
   * unnecessary for parsing.
   */
  private static String removeUnnecessaryInfo(String attachments) {
    StringBuilder sb = new StringBuilder(attachments);
    Deque<Pair<Integer, Integer>> stack = new ArrayDeque<>();

    int idx = 0;
    int nestingLevel = 0;
    int i = 0;

    while (i < sb.length()) {
      if (sb.charAt(i) == '{') {
        if (nestingLevel > 0) {
          stack.push(Pair.of(idx, nestingLevel));
        }
        idx = i;
        nestingLevel++;
      } else if (sb.charAt(i) == '}') {
        if (stack.getFirst().getSecond() == nestingLevel && nestingLevel > 2) {
          sb.replace(stack.pop().getFirst(), i + 1, "");
          i = idx;
        } else if (nestingLevel > 2) {
          sb.replace(idx, i + 1, "");
          i = idx;
        }
        nestingLevel--;
      }
      i++;
    }
    return sb.toString();
  }

  /**
   * This method accepts {@link Matcher} instance, finding needed tokens inside it and put in map
   * token and value.
   */
  private static Map<String, String> createParsedJsonTokenMap(Matcher matcher) {
    Map<String, String> map = new HashMap<>();
    String tempStr = null;
    boolean prev = false;

    while (matcher.find()) {
      String curr = matcher.group();
      if (curr.equals(TYPE) || curr.equals(OWNER_ID) || curr.equals(MEDIA_ID) || curr.equals(
          ACCESS_KEY) || curr.equals(ID) || curr.equals(STICKER_ID)) {
        tempStr = curr;
        prev = true;
      } else if (prev) {
        if ((tempStr.equals(TYPE)) && (!curr.equals(PHOTO) && !curr.equals(VIDEO) && !curr.equals(
            AUDIO) && (!curr.equals(DOC)) && !curr.equals(STICKER))) {
          continue;
        }
        map.putIfAbsent(tempStr, curr);
        prev = false;
      }
    }
    return map;
  }
}


