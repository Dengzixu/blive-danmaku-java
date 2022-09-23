package net.dengzixu.body;

import net.dengzixu.enums.Operation;

public record StringBody(Operation operation, String data) implements Body {
}
