import net.dengzixu.BLiveDanmakuClient;
import net.dengzixu.enums.Message;
import net.dengzixu.message.content.DanmuContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BLiveDanmakuClientTest {
    private final static Logger logger = LoggerFactory.getLogger(BLiveDanmakuClientTest.class);
    private static final long ROOM_ID = 25059330;


    public static void main(String[] args) {
        BLiveDanmakuClient client = BLiveDanmakuClient.getInstance(ROOM_ID).connect();

        client.addHandler((apexMessageBody -> {
            if (!Message._NULL.equals(apexMessageBody.message())) {

                if (apexMessageBody.content() instanceof DanmuContent) {
                    DanmuContent.EmoticonContent emoticonContent = ((DanmuContent) apexMessageBody.content()).emoticonContent();

                    logger.info("[直播间: {}] {} {}", ROOM_ID,
                            apexMessageBody.convertToString(),
                            emoticonContent != null ? emoticonContent.url() : "");
                } else {
                    logger.info("[直播间: {}] {}", ROOM_ID, apexMessageBody.convertToString());
                }
            }


            // 处理送礼
//            if (Message.SEND_GIFT.equals(apexMessageBody.message())) {
//                logger.info("[直播间: {}] {}", ROOM_ID, apexMessageBody.convertToString());
//            }
        }));
    }
}
