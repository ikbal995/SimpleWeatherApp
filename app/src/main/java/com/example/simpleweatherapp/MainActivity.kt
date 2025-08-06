package com.example.simpleweatherapp

import WeatherResponse
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.remote.errorhandling.WeatherError
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
    val city = remember { mutableStateOf("") }

    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = city.value,
            onValueChange = { city.value = it },
            label = { Text("Enter city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.getWeather(city.value) },
            modifier = Modifier.align(Alignment.End),
            enabled = city.value.isNotEmpty()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val result = state.value) {
            is NetworkState.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkState.Success<WeatherResponse> -> {
                val weather = result.data
                val info = weather.weather.firstOrNull()
                weather

                Text("City: ${weather.cityName}")
                Text("Temperature: ${weather.main.temp}°C")
                Text("Condition: ${info?.mainCondition} (${info?.description})")

                info?.icon?.let {
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${it}@4x.png",
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_launcher_background),
                        modifier = Modifier.size(48.dp)
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

            is NetworkState.None -> {

            }
        }
    }
}