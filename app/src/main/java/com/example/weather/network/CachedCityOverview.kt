package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_overview_table")
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
            clouds=Clouds(default_int),
            coord=Coord(default_double,default_double),
            dt=default_int,
            id=default_int,
            main=Main(default_double,default_int,humidity,default_int,default_int,temp,temp_max,temp_min),
            sys=Sys(country,default_int,default_int,default_int),
            visibility=default_int,
            weather=listOf(Weather(empty_string,empty_string,default_int,empty_string)),
            wind=Wind(default_int,default_double)
        )
    }
}