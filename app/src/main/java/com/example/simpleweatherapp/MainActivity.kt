package com.example.simpleweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.remote.errorhandling.WeatherError
import com.example.data.ui.WeatherModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp(viewModel: WeatherViewModel = hiltViewModel()) {
    val state = viewModel.weatherState.collectAsState()
    val lastCity by viewModel.lastCity.collectAsState()
    var city by remember { mutableStateOf("") }

    LaunchedEffect(lastCity) {
        if (lastCity.isNotEmpty()) {
            city = lastCity
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.getWeather(city) },
            modifier = Modifier.align(Alignment.End),
            enabled = city.isNotEmpty()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))
        val modifier = Modifier.align(Alignment.CenterHorizontally)
        when (val result = state.value) {
            is NetworkState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is NetworkState.Success<WeatherModel> -> {
                val weather = result.data
                val info = weather.weatherInfo.firstOrNull()

                Text("City: ${weather.cityName}", modifier = modifier)
                Text("Temperature: ${weather.temperature}°C", modifier = modifier)
                Text(
                    "Condition: ${info?.mainCondition} (${info?.description})",
                    modifier = modifier
                )

                info?.icon?.let {
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${it}@4x.png",
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_launcher_background),
                        modifier = modifier.size(48.dp)
                    )
                }
            }

            is NetworkState.Error -> {
                val message = when (result.throwable) {
                    is WeatherError.CityNotFound -> "City not found"
                    is WeatherError.NetworkError -> "Check your internet connection"
                    else -> "Unexpected error"
                }
                Text(message, color = Color.Red)
            }

            is NetworkState.None -> {}
        }
    }
}