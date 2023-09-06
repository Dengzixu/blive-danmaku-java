package net.dengzixu.bilvedanmaku;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.bilvedanmaku.api.bilibili.live.BiliBiliAPI;
import net.dengzixu.bilvedanmaku.api.bilibili.live.BiliBiliLiveAPI;
import net.dengzixu.bilvedanmaku.body.AuthBody;
import net.dengzixu.bilvedanmaku.body.BodyResolver;
import net.dengzixu.bilvedanmaku.enums.Operation;
import net.dengzixu.bilvedanmaku.enums.ProtocolVersion;
import net.dengzixu.bilvedanmaku.filter.Filter;
import net.dengzixu.bilvedanmaku.handler.Handler;
import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;
import net.dengzixu.bilvedanmaku.packet.Packet;
import net.dengzixu.bilvedanmaku.packet.PacketBuilder;
import net.dengzixu.bilvedanmaku.packet.PacketResolver;
import net.dengzixu.bilvedanmaku.profile.BLiveAuthProfile;
import net.dengzixu.bilvedanmaku.profile.BLiveServerProfile;
import net.dengzixu.bilvedanmaku.profile.BLiveWebsocketClientProfile;
import net.dengzixu.bilvedanmaku.websocket.client.BLiveWebsocketClient;
import net.dengzixu.bilvedanmaku.websocket.client.DefaultBLiveWebsocketClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class BLiveDanmakuClient {
    private static final Logger logger = LoggerFactory.getLogger(BLiveDanmakuClient.class);

    // 弹幕客户端实例
    private static final Map<Long, BLiveDanmakuClient> instanceMap = new ConcurrentHashMap<>();

    // 房间号
    private final Long roomID;
    // 认证配置
    private final BLiveAuthProfile bLiveAuthProfile;
    // 弹幕服务器配置
    private final BLiveServerProfile bLiveServerProfile;
    // Websocket 客户端配置
    private final BLiveWebsocketClientProfile bLiveWebsocketClientProfile;


    // Handler 列表
    private final CopyOnWriteArrayList<Handler> handlers = new CopyOnWriteArrayList<>();
    // Filter 列表
    private final Map<String, Filter> filters = new ConcurrentHashMap<>();

    // WebsocketClient
    private final BLiveWebsocketClient websocketClient;

    // 线程池
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private BLiveDanmakuClient(Long roomID,
                               BLiveAuthProfile bLiveAuthProfile,
                               BLiveServerProfile bLiveServerProfile,
                               BLiveWebsocketClientProfile bLiveWebsocketClientProfile) {
        this.roomID = roomID;
        this.bLiveAuthProfile = bLiveAuthProfile;
        this.bLiveServerProfile = bLiveServerProfile;
        this.bLiveWebsocketClientProfile = bLiveWebsocketClientProfile;

        this.websocketClient = new DefaultBLiveWebsocketClient(this.roomID,
                this.bLiveServerProfile,
                this.bLiveWebsocketClientProfile,
                this.createWebsocketListener());
    }

    /**
     * 创建弹幕客户端
     *
     * @param roomID 房间号
     * @return BLiveDanmakuClient
     */
    public static BLiveDanmakuClient getInstance(Long roomID) {
        return getInstance(roomID,
                BLiveAuthProfile.getAnonymous(),
                BLiveServerProfile.getDefault(),
                BLiveWebsocketClientProfile.getDefault());
    }

    /**
     * 创建弹幕客户端
     *
     * @param roomID           房间号
     * @param bLiveAuthProfile 认证信息
     * @return BLiveDanmakuClient
     */
    public static BLiveDanmakuClient getInstance(Long roomID, BLiveAuthProfile bLiveAuthProfile) {
        return getInstance(roomID,
                bLiveAuthProfile,
                BLiveServerProfile.getDefault(),
                BLiveWebsocketClientProfile.getDefault());
    }

    /**
     * 创建弹幕客户端
     *
     * @param roomID 房间号
     * @return BLiveDanmakuClient
     */
    public static BLiveDanmakuClient getInstance(long roomID) {
        return getInstance(Long.valueOf(roomID));
    }

    /**
     * 创建弹幕客户端
     *
     * @param roomID           房间号
     * @param bLiveAuthProfile 认证信息
     * @return BLiveDanmakuClient
     */
    public static BLiveDanmakuClient getInstance(long roomID, BLiveAuthProfile bLiveAuthProfile) {
        return getInstance(Long.valueOf(roomID), bLiveAuthProfile);
    }

    /**
     * 创建弹幕客户端
     *
     * @param roomID                      房间号
     * @param bLiveServerProfile          弹幕服务器配置
     * @param bliveWebsocketClientProfile Websocket 客户端配置
     * @return BLiveDanmakuClient
     */
    public static BLiveDanmakuClient getInstance(Long roomID,
                                                 BLiveAuthProfile bLiveAuthProfile,
                                                 BLiveServerProfile bLiveServerProfile,
                                                 BLiveWebsocketClientProfile bliveWebsocketClientProfile) {
        if (null == instanceMap.get(roomID)) {
            instanceMap.put(roomID, new BLiveDanmakuClient(roomID,
                    bLiveAuthProfile,
                    bLiveServerProfile,
                    bliveWebsocketClientProfile));
        }

        return instanceMap.get(roomID);
    }

    /**
     * 添加 Handler
     * <b>Handler 没有先后顺序</b>
     *
     * @param handler Handler 对象
     * @return BLiveDanmakuClient Chain
     */
    public BLiveDanmakuClient addHandler(Handler handler) {
        handlers.add(handler);

        return this;
    }

    /**
     * 移除 Handler
     *
     * @param handler Handler 对象
     * @return BLiveDanmakuClient Chain
     */
    public BLiveDanmakuClient removeHandler(Handler handler) {
        handlers.remove(handler);

        return this;

    }


    /**
     * 添加 Filter
     * <b>Filter 没有先后顺序</b>
     *
     * @param type   类型
     * @param filter Filter
     * @return BLiveDanmakuClient Chain
     */
    public BLiveDanmakuClient addFilter(String type, Filter filter) {
        filters.put(type, filter);

        return this;
    }

    /**
     * 链接弹幕服务器
     *
     * @return BLiveDanmakuClient Chain
     */
    public BLiveDanmakuClient connect() {
        this.websocketClient.connect();
        return this;
    }

    /**
     * 断开弹幕服务器
     *
     * @return BLiveDanmakuClient Chain
     */
    public BLiveDanmakuClient disconnect() {
        this.websocketClient.disconnect();

        return this;
    }

    private WebSocketListener createWebsocketListener() {
        return new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("Closed");
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                logger.error("[直播间: {}] 连接意外断开\n", roomID, t);

                websocketClient.reconnect();
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                // 解析数据包
                List<Packet> packetList = new PacketResolver(bytes.toByteArray()).resolve();

                packetList.forEach(packet -> {
                    try {
                        SimpleMessageBody<?> simpleMessageBody = new BodyResolver(packet).resolverAsApexMessageBody();

                        handlers.forEach(handler -> executor.execute(() -> {
                            handler.doHandler(simpleMessageBody);
                        }));

                    } catch (Exception e) {
                        logger.error("[直播间: {}] 发生未知错误。\nPacket:{}",
                                roomID,
                                packet,
                                e);
                    }
                });
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                logger.info("[直播间: {}] 准备建立链接", roomID);

                try {
                    this.auth(webSocket);
                } catch (Exception e) {
                    logger.error("[直播间: {}] 认证失败", roomID, e);
                }

            }

            public void auth(WebSocket webSocket) throws Exception {
                logger.info("[直播间: {}] 进行身份认证……", roomID);

                BiliBiliLiveAPI biliBiliLiveAPI = new BiliBiliLiveAPI();
                BiliBiliAPI biliBiliAPI = new BiliBiliAPI();

                // 获取 Token
                JsonNode danmuInfoJsonNode = new ObjectMapper()
                        .readValue(biliBiliLiveAPI.getDanmuInfo(roomID, bLiveAuthProfile.sessData()), JsonNode.class);
                String token = danmuInfoJsonNode.get("data").get("token").asText();

                // 获取房间对应的主播的 UID
//                long anchorUID = (int) biliBiliLiveAPI.roomInit(roomID).get("room_id");
//                logger.info("[直播间: {}] 主播 UID: {}", roomID, anchorUID);

                Long authUID = bLiveAuthProfile.uid();
                // 判断 UID 为 0 时，或 sessData 为空时，如果未设置，UID 取 0
                if (authUID.equals(0L) || StringUtils.isBlank(bLiveAuthProfile.sessData())) {
                    logger.warn("[直播间: {}] 未设置认证 UID，使用匿名模式。建议正确配置 UID 与 SESSDATA 避免出现连接中断等问题。", roomID);
                    authUID = 0L;
                } else if (!authUID.equals(0L) && StringUtils.isBlank(bLiveAuthProfile.sessData())) {
                    logger.warn("[直播间: {}] 已设置认证 UID，但未设置 SESSDATA，使用匿名模式。建议正确配置 UID 与 SESSDATA 避免出现连接中断等问题", roomID);

                    authUID = 0L;
                }
                logger.info("[直播间: {}] 使用认证 UID: {}", roomID, authUID);


                // 获取 buvid3

                JsonNode spiJsonNode = new ObjectMapper()
                        .readValue(biliBiliAPI.spi(), JsonNode.class);

                String buvid = spiJsonNode.get("data").get("b_3").asText();
                logger.info("[直播间: {}] 获取到 buvid: {}", roomID, buvid);

                // 构建 Packet
                Packet packet = PacketBuilder.newBuilder()
                        .protocolVersion(ProtocolVersion.HEARTBEAT)
                        .operation(Operation.USER_AUTHENTICATION)
                        .body(new AuthBody(authUID, roomID, buvid, token).toJsonBytes())
                        .build();

                // 发送 Packet
                webSocket.send(new ByteString(packet.getBytes()));
                // 发送心跳包
                websocketClient.startHeartbeat();
            }
        };
    }
}
