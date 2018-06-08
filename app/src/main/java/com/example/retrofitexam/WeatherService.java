package com.example.retrofitexam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    String BASE_URL = "http://api.openweathermap.org/data/";
    String APP_ID = "95114a1e948559e010396b4debdf1672";

    @GET("2.5/weather")
    Call<WeatherResult> getWeather(@Query("q") String city,
                                   @Query("appid") String appId);

    @GET("2.5/forecast")
    Call<ForecastResult> getForecast(@Query("q") String city,
                                     @Query("appid") String appId);
}
