package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherApiResponse(
    val cnt: Int,
    val list: List<City>
)

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey val name: String,
    val clouds: Clouds,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

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

data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int = 0
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)

