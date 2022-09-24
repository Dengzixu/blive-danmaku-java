package net.dengzixu.bilvedanmaku.data;

import net.dengzixu.bilvedanmaku.annotation.DataResolver;
import net.dengzixu.bilvedanmaku.enums.Command;
import net.dengzixu.bilvedanmaku.exception.DataFormatException;
import net.dengzixu.bilvedanmaku.message.content.SendGiftContent;
import net.dengzixu.bilvedanmaku.message.metadata.FansMedalMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.TimestampMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserFaceMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = Command.SEND_GIFT)
public class SendGiftResolver extends AbstractDataResolver<SendGiftContent> {
    private final Map<?, ?> dataMap;
    private final Map<?, ?> fansMedalMap;

    public SendGiftResolver(Map<?, ?> bodyMap) {
        super(bodyMap);
        if (bodyMap.get("data") instanceof Map<?, ?> dataMap &&
                dataMap.get("medal_info") instanceof Map<?, ?> fansMedalMap) {
            this.dataMap = dataMap;
            this.fansMedalMap = fansMedalMap;
        } else {
            throw new DataFormatException();
        }
    }

    @Override
    public SendGiftContent resolveMessageContent() {
        return new SendGiftContent(((String) dataMap.get("action")),
                ((String) dataMap.get("batch_combo_id")),
                dataMap.get("batch_combo_send"),
                ((String) dataMap.get("coin_type")),
                ((Number) dataMap.get("discount_price")).intValue(),
                ((Number) dataMap.get("giftId")).longValue(),
                ((String) dataMap.get("giftName")),
                ((Number) dataMap.get("giftType")).intValue(),
                ((boolean) dataMap.get("is_first")),
                ((Number) dataMap.get("num")).intValue(),
                ((Number) dataMap.get("price")).intValue(),
                ((Number) dataMap.get("total_coin")).longValue()
        );
    }

    @Override
    public UserMetadata resolveUserMetadata() {
        return new UserMetadata(((Number) dataMap.get("uid")).longValue(), ((String) dataMap.get("uname")));
    }

    @Override
    public UserFaceMetadata resolveUserFaceMetadata() {
        return new UserFaceMetadata(((String) dataMap.get("face")));
    }

    @Override
    public FansMedalMetadata resolveFansMedalMetadata() {
        return new FansMedalMetadata(((Number) fansMedalMap.get("target_id")).longValue(),
                ((Number) fansMedalMap.get("medal_level")).intValue(),
                ((String) fansMedalMap.get("medal_name")),
                ((Number) fansMedalMap.get("medal_color")).intValue(),
                ((Number) fansMedalMap.get("medal_color_start")).intValue(),
                ((Number) fansMedalMap.get("medal_color_end")).intValue(),
                ((Number) fansMedalMap.get("medal_color_border")).intValue(),
                ((Number) fansMedalMap.get("is_lighted")).intValue() == 1,
                ((Number) fansMedalMap.get("guard_level")).intValue(),
                ((String) fansMedalMap.get("special")),
                ((Number) fansMedalMap.get("icon_id")).intValue(),
                ((Number) fansMedalMap.get("anchor_roomid")).longValue(),
                0);
    }

    @Override
    public TimestampMetadata resolveTimestampMetadata() {
        return new TimestampMetadata(((Number) dataMap.get("timestamp")).longValue());
    }
}
