package net.dengzixu.bilvedanmaku.message.content;

public record WatchedChangeContent(Integer num, String textSmall, String textLarge) implements MessageContent {
    /**
     * @param num       看过的人数
     * @param textSmall 看过的人数短
     * @param textLarge 看过的人数长
     */
    public WatchedChangeContent(Integer num, String textSmall, String textLarge) {
        this.num = num;
        this.textSmall = textSmall;
        this.textLarge = textLarge;
    }

    @Override
    public String convertToString() {
        return textLarge;
    }
}
