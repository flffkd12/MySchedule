package com.example.myschedule.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherInfoList(
        @Query("baseDate") baseDate: String,
        @Query("baseTime") baseTime: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ): WeatherResponse
}
