package com.example.weather.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_details_table")
data class CachedCityDetails(
    @PrimaryKey val cityId: Int,
    val name: String,
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
            base= EMPTY_STRING,
            cod=default_int,
            timezone=default_int,
            name=name,
            clouds=Clouds(default_int),
            coord=Coord(default_double,default_double),
            dt=default_int,
            id=-1,
            main=Main(default_double,default_int,humidity,pressure,default_int,temp,temp_max,temp_min),
            sys=Sys(country,sunrise,sunset,default_int),
            visibility=default_int,
            weather= mutableListOf(Weather(description, icon, default_int, EMPTY_STRING)),
            wind=Wind(default_int,wind)
        )
    }
}