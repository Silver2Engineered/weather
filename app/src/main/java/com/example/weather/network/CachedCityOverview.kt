package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_overview_table")
data class CachedCityOverview(
    @PrimaryKey val cityId: Int,
    val name: String,
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
            id=cityId,
            main=Main(default_double,default_int,humidity,default_int,default_int,temp,temp_max,temp_min),
            sys=Sys(country,default_int,default_int,default_int),
            visibility=default_int,
            weather=listOf(Weather(EMPTY_STRING,EMPTY_STRING,default_int,EMPTY_STRING)),
            wind=Wind(default_int,default_double)
        )
    }
}