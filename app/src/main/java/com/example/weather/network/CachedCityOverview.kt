package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CachedCityOverview(
    @PrimaryKey val name: String,
    val country: String,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val humidity: Int
)
{
    fun toDomainModel(): CityOverview {
        return CityOverview(
            name=name,
            clouds=Clouds(-1),
            coord=Coord(-1.0,-1.0),
            dt=-1,
            id=-1,
            main=Main(-1.0,-1,humidity,-1,-1,temp,temp_max,temp_min),
            sys=Sys(country,-1,-1,-1),
            visibility=-1,
            weather=listOf(Weather("","",-1,"")),
            wind=Wind(-1,-1.0)
        )
    }
}