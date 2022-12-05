package net.dengzixu.bilvedanmaku.profile;

public record BLiveServerProfile(String host, String port) {
    public static BLiveServerProfile getDefault() {
        return new BLiveServerProfile("broadcastlv.chat.bilibili.com", "443");
    }
}
