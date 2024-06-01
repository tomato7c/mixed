package com.example.mixed.qt;

import com.example.mixed.RealTimeInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QtService {
    @GET("q")
    public Call<RealTimeInfo> realTime(@Query("q") String code);
}
