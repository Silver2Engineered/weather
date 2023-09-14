package com.example.weather.network

/**
 * This data class defines a City which includes an name, country, currentTemp, highTemp, lowTemp, and rainChance
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class City(
    val cityName: String,
    val country: String,
    val currentTemp: String,
    val highTemp: String,
    val lowTemp: String,
    val rainChance: String
)