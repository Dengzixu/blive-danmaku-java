package net.dengzixu.data;

import net.dengzixu.annotation.DataResolver;
import net.dengzixu.enums.Command;
import net.dengzixu.exception.DataFormatException;
import net.dengzixu.message.content.WatchedChangeContent;
import net.dengzixu.message.metadata.FansMedalMetadata;
import net.dengzixu.message.metadata.TimestampMetadata;
import net.dengzixu.message.metadata.UserFaceMetadata;
import net.dengzixu.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = Command.WATCHED_CHANGE)
public class WatchedChangeResolver extends AbstractDataResolver<WatchedChangeContent> {
    private final Map<?, ?> dataMap;

    public WatchedChangeResolver(Map<?, ?> bodyMap) {
        super(bodyMap);
        if (bodyMap.get("data") instanceof Map<?, ?> dataMap) {
            this.dataMap = dataMap;
        } else {
            throw new DataFormatException();
        }
    }

    @Override
    public WatchedChangeContent resolveMessageContent() {
        return new WatchedChangeContent(((Number) dataMap.get("num")).intValue(),
                dataMap.get("text_small").toString(),
                dataMap.get("text_large").toString());
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
        return new TimestampMetadata(Math.floorDiv(System.currentTimeMillis(), 1000), Boolean.TRUE);
    }
}
