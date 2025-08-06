package com.example.simpleweatherapp

import WeatherResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _weatherState =
        MutableStateFlow<NetworkState<WeatherResponse>>(NetworkState.None)
    val weatherState: StateFlow<NetworkState<WeatherResponse>> = _weatherState.asStateFlow()

    fun getWeather(city: String) {
        viewModelScope.launch {
            repository.fetchWeather(city).collect {
                _weatherState.value = it
            }
        }
    }
}