package net.dengzixu.bilvedanmaku.body;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.bilvedanmaku.annotation.processor.DataResolverAnnotationProcessor;
import net.dengzixu.bilvedanmaku.enums.Command;
import net.dengzixu.bilvedanmaku.enums.Message;
import net.dengzixu.bilvedanmaku.enums.Operation;
import net.dengzixu.bilvedanmaku.exception.DataFormatException;
import net.dengzixu.bilvedanmaku.exception.UnknownCommandException;
import net.dengzixu.bilvedanmaku.exception.UnknownOperationException;
import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;
import net.dengzixu.bilvedanmaku.message.content.*;
import net.dengzixu.bilvedanmaku.message.metadata.FansMedalMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.TimestampMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserFaceMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserMetadata;
import net.dengzixu.bilvedanmaku.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

public class BodyResolver {
    private static final Logger logger = LoggerFactory.getLogger(BodyResolver.class);

    private final Operation operation;
    private final byte[] body;

    /**
     * @param operation Operation
     * @param body      Bodu
     */
    public BodyResolver(Operation operation, byte[] body) {
        this.operation = operation;
        this.body = body;
    }

    /**
     * @param packet Packet
     */
    public BodyResolver(Packet packet) {
        this(packet.operation(), packet.body());
    }

    public StringBody resolveAsStringBody() {
        return switch (operation) {
            case HEARTBEAT_REPLY ->
                    new StringBody(this.operation, String.valueOf(ByteBuffer.wrap(body).order(ByteOrder.BIG_ENDIAN).getInt()));
            case MESSAGE -> new StringBody(this.operation, new String(body));
            case CONNECT_SUCCESS -> new StringBody(this.operation, "AUTH SUCCESS");
            default -> throw new UnsupportedOperationException();
        };
    }

    public SimpleMessageBody<?> resolverAsApexMessageBody() {
        switch (operation) {
            // ?????????????????????
            case HEARTBEAT_REPLY -> {
                Integer popular = ByteBuffer.wrap(body).order(ByteOrder.BIG_ENDIAN).getInt();
                return new SimpleMessageBody<>(null,
                        null,
                        null,
                        new TimestampMetadata(Math.floorDiv(System.currentTimeMillis(), 1000), Boolean.TRUE),
                        Message._POPULAR,
                        new PopularContent(popular));
            }
            // ????????????
            case MESSAGE -> {
                final String message = new String(body);

                try {
                    final Map<?, ?> messageMap = new ObjectMapper().readValue(message, new TypeReference<>() {
                    });

                    // 2022-12-06 ??? DANMU_MSG ??????????????????
                    String CMD = (String) messageMap.get("cmd");

                    if (CMD.contains("DANMU_MSG")) {
                        CMD = "DANMU_MSG";
                    }

                    // ??????????????? Resolver
                    Class<?> clazz = DataResolverAnnotationProcessor.COMMAND_MAP.get(Command.valueOf(CMD));

                    // ??????????????????????????? Resolver
                    if (null != clazz) {
                        Object instance = clazz.getDeclaredConstructor(Map.class).newInstance(messageMap);

                        UserMetadata userMetadata = (UserMetadata) clazz.getMethod("resolveUserMetadata").invoke(instance);
                        UserFaceMetadata userFaceMetadata = (UserFaceMetadata) clazz.getMethod("resolveUserFaceMetadata").invoke(instance);
                        FansMedalMetadata fansMedalMetadata = (FansMedalMetadata) clazz.getMethod("resolveFansMedalMetadata").invoke(instance);
                        TimestampMetadata timestampMetadata = (TimestampMetadata) clazz.getMethod("resolveTimestampMetadata").invoke(instance);

                        MessageContent messageContent = (MessageContent) clazz.getMethod("resolveMessageContent").invoke(instance);

                        return new SimpleMessageBody<>(userMetadata,
                                userFaceMetadata,
                                fansMedalMetadata,
                                timestampMetadata,
                                messageContent instanceof NullContent ? Message._NULL : Message.valueOf(CMD),
                                messageContent);
                    } else {
                        logger.warn("?????????[{}]????????? Resolver", messageMap.get("cmd"));

                        return new SimpleMessageBody<>(null,
                                null,
                                null,
                                new TimestampMetadata(Math.floorDiv(System.currentTimeMillis(), 1000), Boolean.TRUE),
                                Message._RAW,
                                new RAWContent(message));
                    }
                } catch (JsonProcessingException e) {
                    logger.error("?????????????????????????????????: {}", message, e);
                    throw new DataFormatException();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e) {
                    logger.error("?????? DataResolver ????????????", e);
                } catch (IllegalArgumentException e) {
//                    logger.warn("??????????????????Command, {}", e.getMessage());
                    throw new UnknownCommandException();
                }
            }
            // ????????????
            case CONNECT_SUCCESS -> {
                return new SimpleMessageBody<>(null,
                        null,
                        null,
                        new TimestampMetadata(Math.floorDiv(System.currentTimeMillis(), 1000), Boolean.TRUE),
                        Message._AUTH_SUCCESS,
                        new AuthSuccessContent());
            }
        }

        // ???????????????????????????
        throw new UnknownOperationException();
    }
}
