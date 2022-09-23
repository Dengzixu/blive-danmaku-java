package net.dengzixu.message.content;

public record RAWContent(String rawContent) implements MessageContent {
    @Override
    public String convertToString() {
        return toString();
    }
}
