package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CachedCityOverview(
    @PrimaryKey val name: String,
    val main: Main,
    val sys: Sys
)
{
    fun toDomainModel(): CityOverview {
        return CityOverview(
            name=name,
            clouds=Clouds(-1),
            coord=Coord(-1.0,-1.0),
            dt=-1,
            id=-1,
            main=main,
            sys=sys,
            visibility=-1,
            weather=listOf(Weather("","",-1,"")),
            wind=Wind(-1,-1.0)
        )
    }
}