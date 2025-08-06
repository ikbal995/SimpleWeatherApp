package com.example.simpleweatherapp

import com.example.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun bindMyRepository(remoteDataSource: RemoteDataSource): WeatherRepository =
        WeatherRepositoryImpl(remoteDataSource)
}