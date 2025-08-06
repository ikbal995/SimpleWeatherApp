package com.example.data.remote

import WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): WeatherResponse
}