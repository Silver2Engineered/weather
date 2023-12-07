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
            cityId=id,
            name=name,
            country=sys.country,
            temp=main.temp,
            temp_max=main.temp_max,
            temp_min=main.temp_min,
            humidity=main.humidity,
            wind=wind.speed,
            pressure=main.pressure,
            sunrise=sys.sunrise,
            sunset=sys.sunset,
            icon=weather[0].icon,
            description=weather[0].description,
            timezone=timezone
        )
    }
}
