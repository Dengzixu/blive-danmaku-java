package net.dengzixu.bilvedanmaku.message.content;

public record NullContent() implements MessageContent {
    @Override
    public String convertToString() {
        return null;
    }
}