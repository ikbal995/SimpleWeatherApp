package com.example.data.ui

data class WeatherModel(
    val cityName: String,
    val weatherInfo: List<WeatherInfoModel>,
    val temperature: Double
)

data class WeatherInfoModel(val mainCondition: String, val description: String, val icon: String)