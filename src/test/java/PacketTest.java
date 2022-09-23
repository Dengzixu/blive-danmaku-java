import net.dengzixu.api.bilibili.live.BiliBiliLiveAPI;
import net.dengzixu.body.AuthBody;
import net.dengzixu.enums.Operation;
import net.dengzixu.enums.ProtocolVersion;
import net.dengzixu.packet.Packet;
import net.dengzixu.packet.PacketBuilder;
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
