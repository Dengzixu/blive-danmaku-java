package net.dengzixu.bilvedanmaku.packet;

import net.dengzixu.bilvedanmaku.constant.Constant;
import net.dengzixu.bilvedanmaku.enums.Operation;
import net.dengzixu.bilvedanmaku.uitls.UncompressUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PacketResolver {
    private static final Logger logger = LoggerFactory.getLogger(PacketResolver.class);

    private final byte[] rawData;

    public PacketResolver(byte[] rawData) {
        this.rawData = rawData;
    }

    public PacketResolver(String base64Data) {
        this(Base64.decodeBase64(base64Data));
    }

    public List<Packet> resolve() {
        // 创建 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawData);

        // 解析出来的 Packet List
        List<Packet> packets = new ArrayList<>();

        // Packet 偏移
        int packetOffset = 0;

        while (packetOffset < byteBuffer.capacity()) {
            int packetLength = byteBuffer.getInt(packetOffset + Constant.PACKAGE_OFFSET);

            // 构造 Packet
            PacketBuilder packetBuilder = PacketBuilder.newBuilder()
                    .packetLength(packetLength)
                    .headerLength(byteBuffer.getShort(packetOffset + Constant.HEADER_OFFSET))
                    .protocolVersion(byteBuffer.getShort(packetOffset + Constant.VERSION_OFFSET))
                    .operation(byteBuffer.getInt(packetOffset + Constant.OPERATION_OFFSET))
                    .sequence(byteBuffer.getInt(packetOffset + Constant.SEQUENCE_OFFSET));

            // 读取 IBody
            byteBuffer.position(packetOffset + Constant.BODY_OFFSET);
            byte[] body = new byte[packetLength - 16];
            byteBuffer.get(body);
            packetBuilder.body(body);

            byteBuffer.position(0);

            // 构建
            Packet packet = packetBuilder.build();
            packets.add(packet);

            // 特殊处理
            // 目前 现在当 Operation 为 3 (HEARTBEAT_REPLY) 时
            // IBody 尾部会添加 发送心跳时 IBody 的内容吗，但是Packet Length 并不包括这部分内容
            // 不清楚算不算 BUG
            if (Operation.HEARTBEAT_REPLY.equals(packet.operation())) {
                break;
            }

            packetOffset += packetLength;
        }

        // 当 Packet 大于一个的时候 就直接返回
        if (packets.size() > 1) {
            return packets;
        }

        return switch (packets.get(0).protocolVersion()) {
            case NORMAL, HEARTBEAT -> packets;
            case ZLIB -> new PacketResolver(UncompressUtils.uncompress(packets.get(0).body(), UncompressUtils.Compressor.ZLIB)).resolve();
            case BROTLI -> new PacketResolver(UncompressUtils.uncompress(packets.get(0).body(), UncompressUtils.Compressor.BROTLI)).resolve();
            case UNKNOWN -> new ArrayList<>();
        };

    }
}
