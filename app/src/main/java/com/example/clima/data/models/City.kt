package com.example.clima.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String
)
