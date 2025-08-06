package com.example.simpleweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.DataStoreManager
import com.example.data.ui.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    private val _weatherState =
        MutableStateFlow<NetworkState<WeatherModel>>(NetworkState.None)
    val weatherState: StateFlow<NetworkState<WeatherModel>> = _weatherState.asStateFlow()

    private val _lastCity = MutableStateFlow("")
    val lastCity: StateFlow<String> = _lastCity

    init {
        viewModelScope.launch {
            dataStoreManager.lastCity.collect { savedCity ->
                savedCity?.let {
                    _lastCity.value = it
                }
            }
        }
    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            repository.fetchWeather(city).collect {
                _weatherState.value = it
                if (it is NetworkState.Success) {
                    dataStoreManager.saveLastCity(city)
                }
            }
        }
    }
}