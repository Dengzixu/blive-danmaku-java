package net.dengzixu.bilvedanmaku.api.bilibili.live;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.Map;

public class BiliBiliLiveAPI {
    private static final Logger logger = LoggerFactory.getLogger(BiliBiliLiveAPI.class);

    private final API api;

    public BiliBiliLiveAPI() {
        this.api = RetrofitUtils.create(API.class, "https://api.live.bilibili.com");
    }

    public Map<?, ?> roomInit(Long roomID) {
        try {
            Map<String, Object> result = RetrofitUtils.sendRequest(api.roomInit(roomID));
            if (result.get("data") instanceof Map<?, ?> data) {
                return data;
            }
        } catch (IOException e) {
            logger.error("API [https://api.live.bilibili.com/room/v1/Room/room_init] 请求错误", e);
        }
        return null;
    }

    public Map<?, ?> roomInit(long roomID) {
        return this.roomInit(Long.valueOf(roomID));
    }

    public Map<?, ?> getConf(Long roomID) {
        try {
            Map<String, Object> result = RetrofitUtils.sendRequest(api.getConf(roomID));
            if (result.get("data") instanceof Map<?, ?> data) {
                return data;
            }
        } catch (IOException e) {
            logger.error("API [https://api.live.bilibili.com/room/v1/Danmu/getConf] 请求错误", e);
        }
        return null;
    }

    public Map<?, ?> getConf(long roomID) {
        return this.getConf(Long.valueOf(roomID));
    }

    public String getDanmuInfo(Long id, String sessData) {
        try {
            String cookie = StringUtils.isNotBlank(sessData) ? "SESSDATA=" + sessData : "";

            return RetrofitUtils.sendRequest(api.getDanmuInfo(id, 0, cookie));
        } catch (IOException e) {
            logger.error("API [https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo] 请求错误", e);
        }

        return "";
    }

    public String getDanmuInfo(long id, String sessData) {
        return this.getDanmuInfo(Long.valueOf(id), sessData);
    }

    private interface API {
        @GET("/room/v1/Room/room_init")
        @Headers({"Content-Type: application/json; charset=UTF-8"})
        Call<Map<String, Object>> roomInit(@Query("id") Long roomID);

        @GET("/room/v1/Danmu/getConf")
        @Headers({"Content-Type: application/json; charset=UTF-8"})
        Call<Map<String, Object>> getConf(@Query("room_id") Long roomID);

        @GET("/xlive/web-room/v1/index/getDanmuInfo")
        @Headers({"Content-Type: application/json; charset=UTF-8"})
        Call<String> getDanmuInfo(@Query("id") Long id, @Query("type") int type, @Header("Cookie") String Cookie);
    }
}
