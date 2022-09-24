package net.dengzixu.bilvedanmaku.data;

import net.dengzixu.bilvedanmaku.annotation.DataResolver;
import net.dengzixu.bilvedanmaku.enums.Command;
import net.dengzixu.bilvedanmaku.exception.DataFormatException;
import net.dengzixu.bilvedanmaku.message.content.DanmuContent;
import net.dengzixu.bilvedanmaku.message.metadata.FansMedalMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.TimestampMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserFaceMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DataResolver(command = Command.DANMU_MSG)
public class DanmuMessageResolver extends AbstractDataResolver<DanmuContent> {

    // DANMU_MESSAGE 的key是info而不是data
    private final List<?> infoList;

    private final List<?> userInfoList;
    private final List<?> fansMedalList;
    private final List<?> baseInfoList;

    public DanmuMessageResolver(Map<?, ?> bodyMap) {
        super(bodyMap);
        if (this.getBodyMap().get("info") instanceof ArrayList<?> infoList &&
                infoList.get(2) instanceof ArrayList<?> userInfoList &&
                infoList.get(3) instanceof ArrayList<?> fansMedalList &&
                infoList.get(0) instanceof List<?> baseInfoList) {
            this.infoList = infoList;
            this.userInfoList = userInfoList;
            this.fansMedalList = fansMedalList;
            this.baseInfoList = baseInfoList;
        } else {
            throw new DataFormatException();

        }
    }

    @Override
    public DanmuContent resolveMessageContent() {
        DanmuContent.EmoticonContent emoticonContent = null;

        // 判断是否为表情包弹幕
        ArrayList<?> propList = (ArrayList<?>) infoList.get(0);

        if ((int) propList.get(12) == 1) {
            Map<?, ?> emoticonMap = (Map<?, ?>) propList.get(13);
            emoticonContent = new DanmuContent.EmoticonContent((String) emoticonMap.get("emoticon_unique"),
                    (String) emoticonMap.get("url"));
        }

        return new DanmuContent((String) infoList.get(1), emoticonContent);
    }

    @Override
    public UserMetadata resolveUserMetadata() {
        return new UserMetadata(((Number) userInfoList.get(0)).longValue(),
                (String) userInfoList.get(1));
    }

    @Override
    public UserFaceMetadata resolveUserFaceMetadata() {
        // DANMU_MSG 不包含头像
        return null;
    }

    @Override
    public FansMedalMetadata resolveFansMedalMetadata() {
        if (!fansMedalList.isEmpty()) {
            return new FansMedalMetadata(
                    ((Number) fansMedalList.get(12)).longValue(),
                    (int) fansMedalList.get(0),
                    (String) fansMedalList.get(1),
                    (int) fansMedalList.get(4),
                    (int) fansMedalList.get(8),
                    (int) fansMedalList.get(9),
                    (int) fansMedalList.get(7),
                    (int) fansMedalList.get(11) == 1,
                    (int) fansMedalList.get(10),
                    (String) fansMedalList.get(5),
                    (int) fansMedalList.get(6),
                    ((Number) fansMedalList.get(3)).longValue(),
                    // NOTICE: DANMU_MSG 中，粉丝牌不包括粉丝牌等级
                    0);
        } else {
            return null;
        }
    }

    @Override
    public TimestampMetadata resolveTimestampMetadata() {
        return new TimestampMetadata(Math.floorDiv((long) baseInfoList.get(4), 1000));
    }
}
