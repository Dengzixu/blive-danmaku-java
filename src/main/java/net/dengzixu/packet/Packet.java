package net.dengzixu.packet;

import net.dengzixu.enums.Operation;
import net.dengzixu.enums.ProtocolVersion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public record Packet(Integer packetLength, Short headerLength, ProtocolVersion protocolVersion,
                     Operation operation, Integer sequence, byte[] body) {

    /**
     * @param packetLength    包长度
     * @param headerLength    头部长度
     * @param protocolVersion 协议版本
     * @param operation       操作类型
     * @param sequence        序号
     * @param body            消息体
     */
    public Packet(Integer packetLength, Short headerLength, ProtocolVersion protocolVersion, Operation operation, Integer sequence, byte[] body) {
        this.packetLength = packetLength;
        this.headerLength = headerLength;
        this.protocolVersion = protocolVersion;
        this.operation = operation;
        this.sequence = sequence;
        this.body = body;
    }

    /**
     * @param packetLength    包长度
     * @param headerLength    头部长度
     * @param protocolVersion 协议版本
     * @param operation       操作类型
     * @param sequence        序号
     */
    public Packet(Integer packetLength, Short headerLength, ProtocolVersion protocolVersion, Operation operation, Integer sequence) {
        this(packetLength, headerLength, protocolVersion, operation, sequence, null);
    }

    /**
     * @param packetLength    包长度
     * @param protocolVersion 协议版本
     * @param operation       操作类型
     * @param body            消息体
     */
    public Packet(Integer packetLength, ProtocolVersion protocolVersion, Operation operation, byte[] body) {
        this(packetLength, (short) 16, protocolVersion, operation, 1, body);
    }

    public byte[] getBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(packetLength);

        // 设置为Big Endian
        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        // Packet Length
        byteBuffer.putInt(packetLength);

        // Header Length
        byteBuffer.putShort(headerLength);

        // Protocol Version
        byteBuffer.putShort(protocolVersion.value());

        // Operation
        byteBuffer.putInt(operation.value());

        // Sequence
        byteBuffer.putInt(sequence);

        if (null != body && body.length > 0) {
            byteBuffer.put(body);
        }

        return byteBuffer.array();
    }

    @Override
    public String toString() {
        return "Packet{" +
                "packetLength=" + packetLength +
                ", headerLength=" + headerLength +
                ", protocolVersion=" + protocolVersion +
                ", operation=" + operation +
                ", sequence=" + sequence +
                ", body=" + (Operation.HEARTBEAT_REPLY.equals(operation) ? ByteBuffer.wrap(body).getInt() : new String(body)) +
                '}';
    }
}
