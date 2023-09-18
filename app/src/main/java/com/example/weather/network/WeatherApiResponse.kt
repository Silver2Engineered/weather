package com.example.weather.network

data class WeatherApiResponse(
    val cnt: Int,
    val list: List<City>
)