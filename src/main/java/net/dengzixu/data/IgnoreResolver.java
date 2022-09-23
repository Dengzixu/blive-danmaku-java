package net.dengzixu.data;

import net.dengzixu.annotation.DataResolver;
import net.dengzixu.enums.Command;
import net.dengzixu.message.content.NullContent;
import net.dengzixu.message.metadata.FansMedalMetadata;
import net.dengzixu.message.metadata.TimestampMetadata;
import net.dengzixu.message.metadata.UserFaceMetadata;
import net.dengzixu.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = {
        Command.COMBO_SEND,
        Command.ROOM_REAL_TIME_MESSAGE_UPDATE,
        Command.POPULARITY_RED_POCKET_NEW,
        Command.HOT_RANK_SETTLEMENT_V2,
        Command.LIVE_INTERACTIVE_GAME,
        Command.COMMON_NOTICE_DANMAKU,
        Command.HOT_RANK_SETTLEMENT,
        Command.STOP_LIVE_ROOM_LIST,
        Command.HOT_RANK_CHANGED_V2,
        Command.HOT_RANK_CHANGED,
        Command.ONLINE_RANK_COUNT,
        Command.DANMU_AGGREGATION,
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
