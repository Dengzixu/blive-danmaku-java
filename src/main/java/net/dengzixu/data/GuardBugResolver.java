package net.dengzixu.data;

import net.dengzixu.annotation.DataResolver;
import net.dengzixu.enums.Command;
import net.dengzixu.exception.DataFormatException;
import net.dengzixu.message.content.GuardBuyContent;
import net.dengzixu.message.metadata.FansMedalMetadata;
import net.dengzixu.message.metadata.TimestampMetadata;
import net.dengzixu.message.metadata.UserFaceMetadata;
import net.dengzixu.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = Command.GUARD_BUY)
public class GuardBugResolver extends AbstractDataResolver<GuardBuyContent> {
    private final Map<?, ?> dataMap;

    public GuardBugResolver(Map<?, ?> bodyMap) {
        super(bodyMap);

        System.out.println(bodyMap);

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
