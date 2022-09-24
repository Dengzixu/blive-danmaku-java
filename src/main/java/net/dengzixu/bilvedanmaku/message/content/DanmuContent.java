package net.dengzixu.bilvedanmaku.message.content;

public record DanmuContent(String text, EmoticonContent emoticonContent) implements MessageContent {
    @Override
    public String convertToString() {
        if (null != this.emoticonContent) {
            return "[表情包] " + text;
        } else {
            return text;
        }
    }

    public record EmoticonContent(String unique, String url) implements MessageContent {
        @Override
        public String convertToString() {
            return null;
        }
    }
}
