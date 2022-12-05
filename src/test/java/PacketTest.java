import net.dengzixu.bilvedanmaku.api.bilibili.live.BiliBiliLiveAPI;
import net.dengzixu.bilvedanmaku.body.AuthBody;
import net.dengzixu.bilvedanmaku.enums.Operation;
import net.dengzixu.bilvedanmaku.enums.ProtocolVersion;
import net.dengzixu.bilvedanmaku.packet.Packet;
import net.dengzixu.bilvedanmaku.packet.PacketBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PacketTest {
    @Test
    void TestPacketBuilder() {

        BiliBiliLiveAPI biliBiliLiveAPI = new BiliBiliLiveAPI();

        String token = (String) biliBiliLiveAPI.getConf(3080147).get("token");

        // 构建认证数据包
        Packet packet = PacketBuilder.newBuilder()
                .protocolVersion(ProtocolVersion.HEARTBEAT)
                .operation(Operation.USER_AUTHENTICATION)
                .body(new AuthBody(3080147, token).toJsonBytes())
                .build();

        System.out.println(Arrays.toString(packet.getBytes()));
    }
}
