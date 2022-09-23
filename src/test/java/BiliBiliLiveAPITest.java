import net.dengzixu.api.bilibili.live.BiliBiliLiveAPI;
import org.junit.jupiter.api.Test;

public class BiliBiliLiveAPITest {
    private final BiliBiliLiveAPI biliBiliLiveAPI = new BiliBiliLiveAPI();

    private static final Long ROOM_ID = 555L;

    @Test
    void testRoomInit() {
        System.out.println(biliBiliLiveAPI.roomInit(ROOM_ID));
    }

    @Test
    void testGetConf() {
        System.out.println(biliBiliLiveAPI.getConf(ROOM_ID));
    }
}
