package net.dengzixu.bilvedanmaku.body;

import net.dengzixu.bilvedanmaku.enums.Operation;

public record StringBody(Operation operation, String data) implements Body {
}
