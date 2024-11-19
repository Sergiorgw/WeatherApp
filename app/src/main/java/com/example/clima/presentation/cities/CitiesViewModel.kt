package com.example.clima.presentation.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clima.data.models.City
import com.example.clima.data.repository.ApiRepository
import kotlinx.coroutines.launch

class CitiesViewModel : ViewModel() {
    private val apiRepository = ApiRepository()

    fun fetchCity(cityName: String, onResult: (City?) -> Unit) {
        viewModelScope.launch {
            try {
                val city = apiRepository.searchCityByName(cityName)
                Log.d("CitiesViewModel", "City found: $city")
                onResult(city)
            } catch (e: Exception) {
                Log.e("CitiesViewModel", "Error fetching city: ${e.message}")
                onResult(null)
            }
        }
    }
}
