package net.dengzixu.body;

import net.dengzixu.enums.Operation;

public record ObjectBody (Operation operation, Object data) implements Body {
}
