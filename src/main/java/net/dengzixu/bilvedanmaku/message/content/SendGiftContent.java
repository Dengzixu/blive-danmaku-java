package net.dengzixu.bilvedanmaku.message.content;

public record SendGiftContent(String action, String batchComboId, Object batchComboSend,
                              String coinType, Integer discountPrice, Long giftId,
                              String giftName, Integer giftType, Boolean first,
                              Integer num, Integer price, Long totalCoin) implements MessageContent {

    /**
     * @param action         操作
     *                       基本都是投喂
     * @param batchComboId   连击 ID
     * @param batchComboSend 连击信息
     * @param coinType       硬币类型
     *                       电池/金瓜子: gold
     *                       银瓜子: silver
     * @param discountPrice  折扣价格
     * @param giftId         礼物 ID
     * @param giftName       礼物名称
     * @param giftType       礼物类型
     * @param first          第一次送礼
     * @param num            数量
     * @param price          原始价格
     * @param totalCoin      总计硬币
     */
    public SendGiftContent(String action, String batchComboId, Object batchComboSend,
                           String coinType, int discountPrice, long giftId,
                           String giftName, int giftType, boolean first,
                           int num, int price, long totalCoin) {
        this(action, batchComboId, batchComboSend,
                coinType, Integer.valueOf(discountPrice), Long.valueOf(giftId),
                giftName, Integer.valueOf(giftType), Boolean.valueOf(first),
                Integer.valueOf(num), Integer.valueOf(price), Long.valueOf(totalCoin));
    }

    @Override
    public String convertToString() {
        return String.format("赠送 %s 共 %d 个", giftName, num);
    }
}
