package com.example.mixed;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.example.mixed.qt.QtConverterFactory;
import com.example.mixed.qt.QtService;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.RateLimiter;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;

@Service
public class RealTimeService {
    private QtService qtService = null;
    private final RateLimiter rateLimiter = RateLimiter.create(0.5);

    ExecutorService executor = Executors.newSingleThreadExecutor();

    private Map<String, RealTimeInfo> cache = new HashMap<>();

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


        executor.execute(() -> {
            while (true) {
                allCodes().forEach(this::load);
                try {
                    Thread.sleep(Duration.ofMinutes(5).toMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public RealTimeInfo get(String code) {
        return cache.get(code);
    }

    public List<String> allCodes() {
        return ImmutableList.of("sh600329", "sz000651", "sh600036", "sh600900", "sh600887");
    }

    private void load(String code) {
        int random = 1 + new Random(System.currentTimeMillis()).nextInt(5); // [1, 5]
        rateLimiter.acquire(random);

        Response<RealTimeInfo> resp = null;
        try {
            resp = qtService.realTime(code).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        checkState(resp.isSuccessful());

        cache.put(code, resp.body());
    }
}
