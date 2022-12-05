package net.dengzixu.bilvedanmaku.body;

import net.dengzixu.bilvedanmaku.enums.Operation;

public record ObjectBody (Operation operation, Object data) implements Body {
}
