package com.example.clima.presentation.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clima.data.models.Weather
import com.example.clima.data.models.Forecast
import com.example.clima.data.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    private val _currentWeather = MutableStateFlow<Weather?>(null)
    val currentWeather: StateFlow<Weather?> = _currentWeather

    private val _forecast = MutableStateFlow<Forecast?>(null)
    val forecast: StateFlow<Forecast?> = _forecast

    fun fetchCurrentWeather(lat: Float, lon: Float) {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Fetching current weather for lat: $lat, lon: $lon")
                val weather = apiRepository.getCurrentWeather(lat, lon)
                Log.d("WeatherViewModel", "Current weather fetched: $weather")
                _currentWeather.value = weather
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching current weather: ${e.message}")
                _currentWeather.value = null
            }
        }
    }

    fun fetchForecast(lat: Float, lon: Float) {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Fetching forecast for lat: $lat, lon: $lon")
                val forecastData = apiRepository.getForecast(lat, lon)
                Log.d("WeatherViewModel", "Forecast data: $forecastData")
                _forecast.value = forecastData
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching forecast: ${e.message}")
                _forecast.value = null
            }
        }
    }
}