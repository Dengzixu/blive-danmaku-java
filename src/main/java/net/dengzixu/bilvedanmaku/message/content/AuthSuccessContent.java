package net.dengzixu.bilvedanmaku.message.content;

public record AuthSuccessContent() implements MessageContent {

    @Override
    public String convertToString() {
        return "认证成功";
    }
}
