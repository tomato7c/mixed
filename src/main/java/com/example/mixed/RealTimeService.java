package com.example.mixed;

import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.mixed.qt.QtConverterFactory;
import com.example.mixed.qt.QtService;
import com.google.common.util.concurrent.RateLimiter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;

@Service
public class RealTimeService {
    private QtService qtService = null;
    private final RateLimiter rateLimiter = RateLimiter.create(0.5);

    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://qt.gtimg.cn")
                .addConverterFactory(new QtConverterFactory())
                .build();
        qtService = retrofit.create(QtService.class);
    }

    public String get(String code) throws IOException {
        int random = 1 + new Random(System.currentTimeMillis()).nextInt(5);
        rateLimiter.acquire(random);

        Response<RealTimeInfo> resp = qtService.realTime(code).execute();
        if (resp.isSuccessful()) {
            return resp.body().toString();
        } else {
            return code + ": fail";
        }
    }
}
