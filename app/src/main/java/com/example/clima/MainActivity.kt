package com.example.clima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clima.presentation.cities.CitiesPage
import com.example.clima.presentation.weather.WeatherPage
import com.example.clima.ui.theme.ClimaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClimaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "cities"
                ) {
                    composable("cities") {
                        CitiesPage(navController = navController)
                    }
                    composable(
                        route = "weather/{lat}/{lon}",
                        arguments = listOf(
                            navArgument("lat") { type = NavType.FloatType },
                            navArgument("lon") { type = NavType.FloatType }
                        )
                    ) { backStackEntry ->
                        val lat = backStackEntry.arguments?.getFloat("lat") ?: 0f
                        val lon = backStackEntry.arguments?.getFloat("lon") ?: 0f
                        WeatherPage(
                            lat = lat,
                            lon = lon,
                            onBackClick = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}