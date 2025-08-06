package com.example.simpleweatherapp

import com.example.data.ui.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchWeather(city: String): Flow<NetworkState<WeatherModel>>
}
