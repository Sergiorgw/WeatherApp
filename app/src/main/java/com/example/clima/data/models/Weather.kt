package com.example.clima.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("main") val weatherMain: WeatherMain,
    @SerialName("weather") val weatherInfo: List<WeatherInfo>,
    val name: String = ""
) {
    val temp: Float get() = weatherMain.temp
    val description: String get() = weatherInfo.firstOrNull()?.description ?: "No description"
    val humidity: Int get() = weatherMain.humidity
    val tempMin: Float get() = weatherMain.tempMin
    val tempMax: Float get() = weatherMain.tempMax
}

@Serializable
data class WeatherMain(
    val temp: Float,
    val humidity: Int,
    @SerialName("temp_min") val tempMin: Float,
    @SerialName("temp_max") val tempMax: Float
)

@Serializable
data class WeatherInfo(
    val main: String,
    val description: String
)