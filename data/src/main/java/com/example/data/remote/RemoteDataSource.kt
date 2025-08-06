package com.example.data.remote

import WeatherResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getWeatherByCity(city: String): WeatherResponse {
        return apiService.getWeatherByCity(city)
    }
}