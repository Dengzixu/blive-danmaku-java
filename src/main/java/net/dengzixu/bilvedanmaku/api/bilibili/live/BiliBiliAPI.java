package net.dengzixu.bilvedanmaku.api.bilibili.live;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.http.GET;

import java.io.IOException;

public class BiliBiliAPI {
    // Logger
    private static final Logger logger = LoggerFactory.getLogger(BiliBiliAPI.class);

    private final API api;


    public BiliBiliAPI() {
        this.api = RetrofitUtils.create(API.class, "https://api.bilibili.com");
    }

    public String spi() {
        try {
            return RetrofitUtils.sendRequest(api.spi());
        } catch (IOException e) {
            logger.error("API [https://api.bilibili.com/x/frontend/finger/spi] 请求错误", e);
        }
        return null;
    }

    private interface API {
        @GET("/x/frontend/finger/spi")
        Call<String> spi();
    }
}
