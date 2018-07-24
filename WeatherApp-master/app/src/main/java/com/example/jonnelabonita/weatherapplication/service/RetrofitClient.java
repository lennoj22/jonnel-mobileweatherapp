package com.example.jonnelabonita.weatherapplication.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jonnel.abonita on 7/24/2018.
 */

public class RetrofitClient {
    private static Retrofit retroInstance;

    public static Retrofit getInstance() {

        int CONNECTION_TIMEOUT = 6000;
        int READ_TIMEOUT = 6000;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        if (retroInstance == null){
            retroInstance = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retroInstance;
    }

    private RetrofitClient() {
    }
}
