package net.dengzixu.message.body;

import net.dengzixu.enums.Message;
import net.dengzixu.message.content.MessageContent;
import net.dengzixu.message.metadata.FansMedalMetadata;
import net.dengzixu.message.metadata.TimestampMetadata;
import net.dengzixu.message.metadata.UserFaceMetadata;
import net.dengzixu.message.metadata.UserMetadata;

import java.util.Optional;

public record SimpleMessageBody<T extends MessageContent>(Optional<UserMetadata> userMetadata,
                                                          Optional<UserFaceMetadata> userFaceMetadata,
                                                          Optional<FansMedalMetadata> fansMedalMetadata,
                                                          Optional<TimestampMetadata> timestampMetadata,
                                                          Message message,
                                                          T content) implements MessageBody {


    public SimpleMessageBody(UserMetadata userMetadata,
                             UserFaceMetadata userFaceMetadata,
                             FansMedalMetadata fansMedalMetadata,
                             TimestampMetadata timestampMetadata,
                             Message message,
                             T content) {
        this(Optional.ofNullable(userMetadata),
                Optional.ofNullable(userFaceMetadata),
                Optional.ofNullable(fansMedalMetadata),
                Optional.ofNullable(timestampMetadata),
                message,
                content);
    }

    @Override
    public String convertToString() {
        StringBuilder stringBuilder = new StringBuilder();

        this.timestampMetadata.ifPresent(o -> stringBuilder.append(o.convertToString()).append(" "));
        this.userMetadata.ifPresent(o -> stringBuilder.append(o.convertToString()).append(" "));
//        this.userFaceMetadata.ifPresent(o -> stringBuilder.append(o.convertToString()).append(" "));
        this.fansMedalMetadata.ifPresent(o -> stringBuilder.append(o.level() > 0 ? o.convertToString() : "").append(" "));

        stringBuilder.append(content.convertToString());

        return stringBuilder.toString();
    }
}
