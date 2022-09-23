package net.dengzixu.message.content;

public record NullContent() implements MessageContent {
    @Override
    public String convertToString() {
        return null;
    }
}