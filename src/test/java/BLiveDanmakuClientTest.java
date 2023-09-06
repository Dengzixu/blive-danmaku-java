import net.dengzixu.bilvedanmaku.BLiveDanmakuClient;
import net.dengzixu.bilvedanmaku.enums.Message;
import net.dengzixu.bilvedanmaku.message.content.DanmuContent;
import net.dengzixu.bilvedanmaku.profile.BLiveAuthProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BLiveDanmakuClientTest {
    private final static Logger logger = LoggerFactory.getLogger(BLiveDanmakuClientTest.class);
    private static final long ROOM_ID = 25059330;


    public static void main(String[] args) {
        // 创建认证信息
        BLiveAuthProfile bLiveAuthProfile = new BLiveAuthProfile(0, "");

        // 创建弹幕客户端实例
        BLiveDanmakuClient client = BLiveDanmakuClient.getInstance(ROOM_ID, bLiveAuthProfile).connect();

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
        }));
    }
}
