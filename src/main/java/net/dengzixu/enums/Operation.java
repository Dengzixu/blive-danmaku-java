package net.dengzixu.enums;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
    HEARTBEAT(2),
    HEARTBEAT_REPLY(3),
    MESSAGE(5),
    USER_AUTHENTICATION(7),
    CONNECT_SUCCESS(8),

    UNKNOWN(-1);

    private static final Map<Integer, Operation> OPERATION_MAP = new HashMap<>();

    static {
        for (Operation item : values()) {
            OPERATION_MAP.put(item.value, item);
        }
    }

    private final Integer value;

    public static Operation valueOf(Integer value) {
        return null == OPERATION_MAP.get(value) ? UNKNOWN : OPERATION_MAP.get(value);
    }

    Operation(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
