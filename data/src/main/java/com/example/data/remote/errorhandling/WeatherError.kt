package com.example.data.remote.errorhandling

import okio.IOException
import retrofit2.HttpException

sealed class WeatherError : Throwable() {
    object CityNotFound : WeatherError()
    object NetworkError : WeatherError()
    data class Unknown(val error: Throwable) : WeatherError()
}

fun mapToWeatherError(e: Throwable): WeatherError {
    return when (e) {
        is HttpException -> {
            if (e.code() == 404) WeatherError.CityNotFound
            else WeatherError.Unknown(e)
        }

        is IOException -> WeatherError.NetworkError
        else -> WeatherError.Unknown(e)
    }
}
