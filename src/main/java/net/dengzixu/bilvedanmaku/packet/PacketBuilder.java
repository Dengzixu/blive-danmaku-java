package net.dengzixu.bilvedanmaku.packet;

import net.dengzixu.bilvedanmaku.enums.Operation;
import net.dengzixu.bilvedanmaku.enums.ProtocolVersion;

import java.nio.charset.StandardCharsets;

public class PacketBuilder {
    private Integer packetLength;
    private Short headerLength;
    private ProtocolVersion protocolVersion;
    private Operation operation;
    private Integer sequence;
    private byte[] body;

    private PacketBuilder() {
    }

    public static PacketBuilder newBuilder() {
        return new PacketBuilder();
    }

    /**
     * 包长度
     * 当 PacketBuilder 未被主动设置时，会自动设置为 Header Length + IBody Length
     *
     * @param packetLength 长度
     * @return PacketBuilder Chain
     */
    public PacketBuilder packetLength(Integer packetLength) {
        this.packetLength = packetLength;

        return this;
    }

    /**
     * 头部长度
     *
     * @param headerLength 长度
     * @return PacketBuilder Chain
     */
    public PacketBuilder headerLength(Short headerLength) {
        this.headerLength = headerLength;
        return this;
    }

    /**
     * 头部长度
     *
     * @param headerLength 长度
     * @return PacketBuilder Chain
     */
    public PacketBuilder headerLength(Integer headerLength) {
        if (headerLength > Short.MAX_VALUE) {
            throw new RuntimeException("Number is out of size");
        }

        return this.headerLength(headerLength.shortValue());
    }

    public PacketBuilder protocolVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;

        return this;
    }

    public PacketBuilder protocolVersion(Short protocolVersion) {
        return this.protocolVersion(ProtocolVersion.valueOf(protocolVersion));
    }

    public PacketBuilder protocolVersion(Integer protocolVersion) {
        if (protocolVersion > Short.MAX_VALUE) {
            throw new RuntimeException("Number is out of size");
        }
        return this.protocolVersion(ProtocolVersion.valueOf(protocolVersion.shortValue()));
    }

    public PacketBuilder operation(Operation operation) {
        this.operation = operation;

        return this;
    }

    public PacketBuilder operation(Integer operation) {
        return this.operation(Operation.valueOf(operation));

    }

    public PacketBuilder sequence(Integer sequence) {
        this.sequence = sequence;

        return this;
    }

    public PacketBuilder body(String body) {
        return this.body(body.getBytes(StandardCharsets.UTF_8));
    }

    public PacketBuilder body(byte[] body) {
        this.body = body;

        return this;
    }

    public Packet build() {
        // 数据检查
        if (null == this.packetLength) {
            if (body == null || body.length == 0) {
                this.packetLength = 16;
            } else {
                this.packetLength = 16 + body.length;
            }
        }

        if (null == this.headerLength) {
            this.headerLength = 16;
        }

        if (null == this.protocolVersion) {
            throw new RuntimeException("Missing Data");
        }

        if (null == this.operation) {
            throw new RuntimeException("Missing Data");
        }

        if (null == this.sequence) {
            this.sequence = 1;
        }

        if (body == null || body.length == 0) {
            return new Packet(this.packetLength, headerLength,
                    this.protocolVersion, this.operation,
                    this.sequence);
        } else {
            return new Packet(this.packetLength, headerLength,
                    this.protocolVersion, this.operation,
                    this.sequence, this.body);
        }
    }
}
