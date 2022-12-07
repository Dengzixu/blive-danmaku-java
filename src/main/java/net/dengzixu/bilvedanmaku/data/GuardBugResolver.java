package net.dengzixu.bilvedanmaku.data;

import net.dengzixu.bilvedanmaku.annotation.DataResolver;
import net.dengzixu.bilvedanmaku.enums.Command;
import net.dengzixu.bilvedanmaku.exception.DataFormatException;
import net.dengzixu.bilvedanmaku.message.content.GuardBuyContent;
import net.dengzixu.bilvedanmaku.message.metadata.FansMedalMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.TimestampMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserFaceMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = Command.GUARD_BUY)
public class GuardBugResolver extends AbstractDataResolver<GuardBuyContent> {
    private final Map<?, ?> dataMap;

    public GuardBugResolver(Map<?, ?> bodyMap) {
        super(bodyMap);

        if (bodyMap.get("data") instanceof Map<?, ?> dataMap) {
            this.dataMap = dataMap;
        } else {
            throw new DataFormatException();

        }
    }

    @Override
    public GuardBuyContent resolveMessageContent() {
        return new GuardBuyContent(((Number) dataMap.get("guard_level")).intValue(),
                ((Number) dataMap.get("num")).intValue());
    }

    @Override
    public UserMetadata resolveUserMetadata() {
        return new UserMetadata(((Number) dataMap.get("uid")).longValue(),
                ((String) dataMap.get("username")));
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
        return new TimestampMetadata(Math.floorDiv(System.currentTimeMillis(), 1000), Boolean.TRUE);
    }
}
