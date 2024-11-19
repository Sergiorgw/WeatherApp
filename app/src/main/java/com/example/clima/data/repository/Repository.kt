package com.example.clima.data.repository

import com.example.clima.data.models.City
import com.example.clima.data.models.Weather
import com.example.clima.data.models.Forecast

interface Repository {
    suspend fun searchCityByName(cityName: String): City?
    suspend fun getCurrentWeather(lat: Float, lon: Float): Weather
    suspend fun getForecast(lat: Float, lon: Float): Forecast
}
