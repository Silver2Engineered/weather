package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CachedCityDetails(
    @PrimaryKey val name: String,
    val main: Main,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind,
    val timezone: Int
)
{
    fun toDomainModel(): CityDetails {
        return CityDetails(
            base="",
            cod=-1,
            timezone=timezone,
            name=name,
            clouds=Clouds(-1),
            coord=Coord(-1.0,-1.0),
            dt=-1,
            id=-1,
            main=main,
            sys=sys,
            visibility=-1,
            weather=weather,
            wind=wind
        )
    }
}