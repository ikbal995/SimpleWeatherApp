package com.example.simpleweatherapp

import WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(city: String): Flow<NetworkState<WeatherResponse>>
}
