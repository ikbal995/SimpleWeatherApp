package com.example.simpleweatherapp

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error(val throwable: Throwable) : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()

    object None : NetworkState<Nothing>()
}