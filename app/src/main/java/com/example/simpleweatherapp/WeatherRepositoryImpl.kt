package com.example.simpleweatherapp

import WeatherResponse
import com.example.data.remote.RemoteDataSource
import com.example.data.remote.errorhandling.mapToWeatherError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    WeatherRepository {
    override fun fetchWeather(city: String): Flow<NetworkState<WeatherResponse>> = flow {
        emit(NetworkState.Loading)
        try {
            val result = remoteDataSource.getWeatherByCity(city)
            emit(NetworkState.Success(result))
        } catch (exception: Exception) {
            emit(NetworkState.Error(mapToWeatherError(exception)))
        }

    }.flowOn(Dispatchers.IO)
}