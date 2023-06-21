package net.dengzixu.bilvedanmaku.data;

import net.dengzixu.bilvedanmaku.annotation.DataResolver;
import net.dengzixu.bilvedanmaku.enums.Command;
import net.dengzixu.bilvedanmaku.message.content.NullContent;
import net.dengzixu.bilvedanmaku.message.metadata.FansMedalMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.TimestampMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserFaceMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = {
        Command.COMBO_SEND,
        Command.POPULARITY_RED_POCKET_WINNER_LIST,
        Command.ROOM_REAL_TIME_MESSAGE_UPDATE,
        Command.POPULARITY_RED_POCKET_NEW,
        Command.WIDGET_GIFT_STAR_PROCESS,
        Command.HOT_RANK_SETTLEMENT_V2,
        Command.LIVE_INTERACTIVE_GAME,
        Command.COMMON_NOTICE_DANMAKU,
        Command.GUARD_HONOR_THOUSAND,
        Command.HOT_RANK_SETTLEMENT,
        Command.STOP_LIVE_ROOM_LIST,
        Command.HOT_RANK_CHANGED_V2,
        Command.LIKE_INFO_V3_UPDATE,
        Command.LIKE_INFO_V3_CLICK,
        Command.LIVE_PANEL_CHANGE,
        Command.ONLINE_RANK_COUNT,
        Command.DANMU_AGGREGATION,
        Command.HOT_RANK_CHANGED,
        Command.ONLINE_RANK_TOP3,
        Command.USER_TOAST_MSG,
        Command.ONLINE_RANK_V2,
        Command.WIDGET_BANNER,
        Command.NOTICE_MSG
})
public class IgnoreResolver extends AbstractDataResolver<NullContent> {
    public IgnoreResolver(Map<?, ?> dataMap) {
        super(dataMap);
    }

    @Override
    public NullContent resolveMessageContent() {
        return new NullContent();
    }

    @Override
    public UserMetadata resolveUserMetadata() {
        return null;
    }

    @Override
    public UserFaceMetadata resolveUserFaceMetadata() {
        return null;
    }

    @Override
    public FansMedalMetadata resolveFansMedalMetadata() {
        return null;
    }

    @Override
    public TimestampMetadata resolveTimestampMetadata() {
        return null;
    }
}
