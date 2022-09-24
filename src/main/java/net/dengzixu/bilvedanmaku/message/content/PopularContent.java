package net.dengzixu.bilvedanmaku.message.content;

public record PopularContent(Integer popular) implements MessageContent {
    @Override
    public String convertToString() {
        return String.format("人气: %d", popular);
    }
}
