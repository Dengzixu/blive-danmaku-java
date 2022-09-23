package net.dengzixu.api.bilibili.live;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.Map;

public class RetrofitUtils {
    private static final Logger logger = LoggerFactory.getLogger(RetrofitUtils.class);

    public static <T> T create(final Class<T> service, String url) {
        OkHttpClient okHttpClient;
        Retrofit retrofit;

        okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .build();
                    return chain.proceed(request);
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(service);
    }

    public static Map<String, Object> sendRequest(Call<Map<String, Object>> call) throws IOException {
        Response<Map<String, Object>> response = call.execute();

        return response.body();
    }
}
