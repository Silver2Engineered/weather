package com.example.weather.network

data class Main(
    val feels_like: Double,
    val grnd_level: Int = -1,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int = -1,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)