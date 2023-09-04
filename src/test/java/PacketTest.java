import net.dengzixu.bilvedanmaku.api.bilibili.live.BiliBiliLiveAPI;
import net.dengzixu.bilvedanmaku.body.AuthBody;
import net.dengzixu.bilvedanmaku.enums.Operation;
import net.dengzixu.bilvedanmaku.enums.ProtocolVersion;
import net.dengzixu.bilvedanmaku.packet.Packet;
import net.dengzixu.bilvedanmaku.packet.PacketBuilder;
import net.dengzixu.bilvedanmaku.packet.PacketResolver;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PacketTest {
    @Test
    void testBuildPingPacket() {
        Packet packet = PacketBuilder.newBuilder()
                .protocolVersion(ProtocolVersion.HEARTBEAT)
                .operation(Operation.HEARTBEAT)
                .build();

        System.out.println("ping packet (Base64): " + Base64.encodeBase64String(packet.getBytes()));
    }

    @Test
    void testDecodeHEXPacket() {
        String hexPacket = "00000010001000010000000200000002";

        try {
            System.out.println(new PacketResolver(Hex.decodeHex(hexPacket)).resolve());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDecodeBase64Packet() {
        String base64Data = "AAAAEAAQAAEAAAACAAAAAQ==";

        try {
            System.out.println(new PacketResolver(base64Data).resolve());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
