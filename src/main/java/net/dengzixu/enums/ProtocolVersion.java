package net.dengzixu.enums;

import java.util.HashMap;
import java.util.Map;

public enum ProtocolVersion {
    NORMAL(0),
    HEARTBEAT(1),
    ZLIB(2),
    BROTLI(3),

    UNKNOWN(-1);

    private static final Map<Short, ProtocolVersion> PROTOCOL_VERSION_MAP = new HashMap<>();

    static {
        for (ProtocolVersion item : values()) {
            PROTOCOL_VERSION_MAP.put(item.value, item);
        }
    }

    private final Short value;

    public static ProtocolVersion valueOf(Integer value) {
        return valueOf(value.shortValue());
    }

    public static ProtocolVersion valueOf(Short value) {
        return null == PROTOCOL_VERSION_MAP.get(value) ? UNKNOWN : PROTOCOL_VERSION_MAP.get(value);
    }

    ProtocolVersion(Integer value) {
        this(value.shortValue());
    }

    ProtocolVersion(Short value) {
        this.value = value;
    }

    public Short value() {
        return this.value;
    }
}
