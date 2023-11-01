package com.example.weather.network


data class CityDetails(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
{
    fun toCachedModel(): CachedCityDetails {
        return CachedCityDetails(
            timezone=timezone,
            name=name,
            main=main,
            sys=sys,
            weather=weather,
            wind=wind
        )
    }
}
