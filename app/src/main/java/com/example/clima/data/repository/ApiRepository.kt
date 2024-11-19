package com.example.clima.data.repository

import android.util.Log
import com.example.clima.data.models.Weather
import com.example.clima.data.models.City
import com.example.clima.data.models.Forecast
import com.example.clima.utils.Constants
import com.example.clima.utils.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class ApiRepository : Repository {
    private val client = HttpClientProvider.client
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override suspend fun searchCityByName(cityName: String): City? {
        val url = "https://api.openweathermap.org/geo/1.0/direct"
        return try {
            val response: List<City> = client.get(url) {
                parameter("q", cityName)
                parameter("limit", 1)
                parameter("appid", Constants.API_KEY)
            }.body()

            Log.d("ApiRepository", "City search response: $response")

            response.firstOrNull()
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error searching city: ${e.message}")
            null
        }
    }

    override suspend fun getCurrentWeather(lat: Float, lon: Float): Weather {
        val url = "https://api.openweathermap.org/data/2.5/weather"
        return try {
            val responseBody = client.get(url) {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("appid", Constants.API_KEY)
                parameter("units", "metric")
            }.body<String>()

            Log.d("ApiRepository", "Raw weather response: $responseBody")

            // Manually parse the JSON to get more detailed error information
            val parsedWeather = json.decodeFromString<Weather>(responseBody)
            Log.d("ApiRepository", "Parsed weather: $parsedWeather")

            parsedWeather
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error fetching current weather: ${e.message}")
            throw e
        }
    }

    override suspend fun getForecast(lat: Float, lon: Float): Forecast {
        val url = "https://api.openweathermap.org/data/2.5/forecast"
        return try {
            client.get(url) {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("appid", Constants.API_KEY)
                parameter("units", "metric")
            }.body()
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error fetching forecast: ${e.message}")
            throw e
        }
    }
}