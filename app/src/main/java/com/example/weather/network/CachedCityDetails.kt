package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CachedCityDetails(
    @PrimaryKey val name: String,
    val country: String,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val humidity: Int,
    val wind: Double,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val icon: String,
    val description: String
)
{
    fun toDomainModel(): CityDetails {
        return CityDetails(
            base="",
            cod=-1,
            timezone=-1,
            name=name,
            clouds=Clouds(-1),
            coord=Coord(-1.0,-1.0),
            dt=-1,
            id=-1,
            main=Main(-1.0,-1,humidity,pressure,-1,temp,temp_max,temp_min),
            sys=Sys(country,sunrise,sunset,-1),
            visibility=-1,
            weather= mutableListOf(Weather(description, icon, -1, "")),
            wind=Wind(-1,wind)
        )
    }
}