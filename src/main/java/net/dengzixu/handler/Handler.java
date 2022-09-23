package net.dengzixu.handler;

import net.dengzixu.message.body.SimpleMessageBody;

public interface Handler {
    void doHandler(SimpleMessageBody<?> simpleMessageBody);
}
