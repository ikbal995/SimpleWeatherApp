package com.example.simpleweatherapp

import com.example.data.remote.RemoteDataSource
import com.example.data.remote.errorhandling.mapToWeatherError
import com.example.data.ui.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import toUiModel
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    WeatherRepository {
    override fun fetchWeather(city: String): Flow<NetworkState<WeatherModel>> = flow {
        emit(NetworkState.Loading)
        try {
            val result = remoteDataSource.getWeatherByCity(city)
            emit(NetworkState.Success(result.toUiModel()))
        } catch (exception: Exception) {
            emit(NetworkState.Error(mapToWeatherError(exception)))
        }

    }.flowOn(Dispatchers.IO)
}