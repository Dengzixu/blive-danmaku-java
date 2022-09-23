package net.dengzixu.data;

import net.dengzixu.annotation.DataResolver;
import net.dengzixu.enums.Command;
import net.dengzixu.exception.DataFormatException;
import net.dengzixu.message.content.InteractWordContent;
import net.dengzixu.message.metadata.FansMedalMetadata;
import net.dengzixu.message.metadata.TimestampMetadata;
import net.dengzixu.message.metadata.UserFaceMetadata;
import net.dengzixu.message.metadata.UserMetadata;

import java.util.Map;

@DataResolver(command = Command.INTERACT_WORD)
public class InteractWordResolver extends AbstractDataResolver<InteractWordContent> {
    private final Map<?, ?> dataMap;
    private final Map<?, ?> fansMedalMap;

    public InteractWordResolver(Map<?, ?> bodyMap) {
        super(bodyMap);
        if (bodyMap.get("data") instanceof Map<?, ?> dataMap &&
                dataMap.get("fans_medal") instanceof Map<?, ?> fansMedalMap) {
            this.dataMap = dataMap;
            this.fansMedalMap = fansMedalMap;
        } else {
            throw new DataFormatException();
        }

    }

    @Override
    public InteractWordContent resolveMessageContent() {
        return new InteractWordContent(((Number) dataMap.get("msg_type")).intValue());
    }

    @Override
    public UserMetadata resolveUserMetadata() {
        return new UserMetadata(((Number) dataMap.get("uid")).longValue(), ((String) dataMap.get("uname")));
    }

    @Override
    public UserFaceMetadata resolveUserFaceMetadata() {
        return null;
    }

    @Override
    public FansMedalMetadata resolveFansMedalMetadata() {
        return FansMedalMetadata.fansMedalConver(fansMedalMap);
    }

    @Override
    public TimestampMetadata resolveTimestampMetadata() {
        return new TimestampMetadata(((Number) dataMap.get("timestamp")).longValue());
    }
}
