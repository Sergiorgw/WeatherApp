package com.example.clima.presentation.weather

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherPage(
    lat: Float,
    lon: Float,
    onBackClick: () -> Unit,
    viewModel: WeatherViewModel = viewModel()
) {
    LaunchedEffect(lat, lon) {
        Log.d("WeatherPage", "Fetching weather for lat: $lat, lon: $lon")
        viewModel.fetchCurrentWeather(lat, lon)
        viewModel.fetchForecast(lat, lon)
    }

    val currentWeather by viewModel.currentWeather.collectAsState()
    val forecast by viewModel.forecast.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Current Weather Display
            currentWeather?.let { weather ->
                Text(
                    text = "Current Weather",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Temperature: ${weather.temp}°C",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Description: ${weather.description}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Humidity: ${weather.humidity}%",
                    style = MaterialTheme.typography.bodyLarge
                )
            } ?: Text("Loading weather data...", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Forecast Display
            forecast?.list?.let { forecastList ->
                Text(
                    text = "Forecast",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                forecastList.take(5).forEach { item ->
                    Text(
                        text = "Temp: ${item.main.temp}°C, Humidity: ${item.main.humidity}%",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } ?: Text("Loading forecast data...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}