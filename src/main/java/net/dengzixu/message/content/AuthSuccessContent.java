package net.dengzixu.message.content;

public record AuthSuccessContent() implements MessageContent {

    @Override
    public String convertToString() {
        return "认证成功";
    }
}
