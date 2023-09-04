import net.dengzixu.bilvedanmaku.api.bilibili.live.BiliBiliAPI;
import org.junit.jupiter.api.Test;

public class BiliBiliAPITest {
    private final BiliBiliAPI biliBiliAPI = new BiliBiliAPI();


    @Test
    void testSpi() {
        System.out.println(biliBiliAPI.spi());
    }
}
