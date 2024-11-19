package com.example.clima.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val list: List<ForecastItem>
)

@Serializable
data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<WeatherDescription>
)

@Serializable
data class Main(
    val temp: Float,
    val temp_min: Float,
    val temp_max: Float,
    val humidity: Int
)

@Serializable
data class WeatherDescription(
    val description: String
)
