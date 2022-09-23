package net.dengzixu.data;

import net.dengzixu.message.content.MessageContent;
import net.dengzixu.message.metadata.FansMedalMetadata;
import net.dengzixu.message.metadata.TimestampMetadata;
import net.dengzixu.message.metadata.UserFaceMetadata;
import net.dengzixu.message.metadata.UserMetadata;

import java.util.Map;

public abstract class AbstractDataResolver<T extends MessageContent> {
    private final Map<?, ?> bodyMap;

    public AbstractDataResolver(Map<?, ?> bodyMap) {
        this.bodyMap = bodyMap;
    }

    protected Map<?, ?> getBodyMap() {
        return bodyMap;
    }

    public abstract T resolveMessageContent();

    public abstract UserMetadata resolveUserMetadata();

    public abstract UserFaceMetadata resolveUserFaceMetadata();

    public abstract FansMedalMetadata resolveFansMedalMetadata();

    public abstract TimestampMetadata resolveTimestampMetadata();
}
