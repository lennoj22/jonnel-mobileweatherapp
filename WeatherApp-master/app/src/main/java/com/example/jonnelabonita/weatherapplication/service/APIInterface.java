package com.example.jonnelabonita.weatherapplication.service;

import com.example.jonnelabonita.weatherapplication.models.OpenWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jonnel.abonita on 7/24/2018.
 */

public interface APIInterface {

    @GET("data/2.5/weather")
    Observable<OpenWeather> getWeatherLondon(
            @Query("q") String location,
            @Query("APPID") String APPID,
            @Query("units") String metric
    );

    @GET("data/2.5/weather")
    Observable<OpenWeather> getWeatherPrague(
            @Query("q") String location,
            @Query("APPID") String APPID,
            @Query("units") String metric
    );

    @GET("data/2.5/weather")
    Observable<OpenWeather> getWeatherSanFrancisco(
            @Query("q") String location,
            @Query("APPID") String APPID,
            @Query("units") String metric
    );

    @GET("data/2.5/weather")
    Observable<OpenWeather> getWeatherCaloocan(
            @Query("lat") String lat,
            @Query("lon") String lng,
            @Query("APPID") String APPID,
            @Query("units") String metric
    );

}
