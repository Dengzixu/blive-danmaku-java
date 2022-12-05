package net.dengzixu.bilvedanmaku.websocket.client;

public abstract class BLiveWebsocketClient {
    /**
     * 建立 Websocket 链接
     */
    public abstract void connect();

    /**
     * 断开 Websocket 链接
     */
    public abstract void disconnect();

    /**
     * 意外断开时，重新建立 Websocket 链接
     */
    public abstract void reconnect();

    public abstract void startHeartbeat();

    public abstract void stopHeartbeat();
}
