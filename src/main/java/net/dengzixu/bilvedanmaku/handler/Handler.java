package net.dengzixu.bilvedanmaku.handler;

import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;

public interface Handler {
    void doHandler(SimpleMessageBody<?> simpleMessageBody);
}
