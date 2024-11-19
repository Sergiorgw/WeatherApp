package com.example.clima.presentation.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.clima.data.models.City

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesPage(
    navController: NavController,
    viewModel: CitiesViewModel = viewModel()
) {
    val popularCities = listOf(
        City("New York", 40.7128f, -74.0060f, "US"),
        City("London", 51.5074f, -0.1278f, "UK"),
        City("Tokyo", 35.6762f, 139.6503f, "JP")
    )

    var cityName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val cleanedCityName = cityName.trim()
                viewModel.fetchCity(cleanedCityName) { city ->
                    if (city != null) {
                        errorMessage = null
                        navController.navigate("weather/${city.lat}/${city.lon}")
                    } else {
                        errorMessage = "Could not find city: $cleanedCityName"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Popular Cities", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(popularCities) { city ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("weather/${city.lat}/${city.lon}")
                        }
                ) {
                    Text(
                        text = city.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Divider()
            }
        }
    }
}