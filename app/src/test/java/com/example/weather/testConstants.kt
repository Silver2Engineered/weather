package com.example.weather

import com.example.weather.network.CachedCityDetails
import com.example.weather.network.CachedCityOverview
import com.example.weather.network.CityDetails
import com.example.weather.network.CityOverview
import com.example.weather.network.Clouds
import com.example.weather.network.Coord
import com.example.weather.network.EMPTY_STRING
import com.example.weather.network.Main
import com.example.weather.network.Sys
import com.example.weather.network.Weather
import com.example.weather.network.Wind
import com.example.weather.network.default_double
import com.example.weather.network.default_int

val nonEmptyString = "test"
val nonDefaultInt = 500
val nonDefaultDouble = 500.0

val testCachedCityDetails = CachedCityDetails(
    cityId = nonDefaultInt,
    name = nonEmptyString,
    country = nonEmptyString,
    temp = nonDefaultDouble,
    temp_max = nonDefaultDouble,
    temp_min = nonDefaultDouble,
    humidity = nonDefaultInt,
    wind = nonDefaultDouble,
    pressure = nonDefaultInt,
    sunrise = nonDefaultInt,
    sunset = nonDefaultInt,
    icon = nonEmptyString,
    description = nonEmptyString,
    timezone = nonDefaultInt
)

val testCityDetails = CityDetails (
    base= EMPTY_STRING,
    cod=default_int,
    timezone= nonDefaultInt,
    name= nonEmptyString,
    clouds= Clouds(default_int),
    coord= Coord(default_double,default_double),
    dt=default_int,
    id= nonDefaultInt,
    main= Main(default_double,default_int, nonDefaultInt,
        nonDefaultInt,default_int, nonDefaultDouble, nonDefaultDouble, nonDefaultDouble),
    sys= Sys(nonEmptyString, nonDefaultInt, nonDefaultInt,default_int),
    visibility=default_int,
    weather= mutableListOf(Weather(nonEmptyString, nonEmptyString, default_int, EMPTY_STRING)),
    wind= Wind(default_int, nonDefaultDouble)
)

val testCachedCityOverview = CachedCityOverview (
    cityId = nonDefaultInt,
    name = nonEmptyString,
    country = nonEmptyString,
    temp = nonDefaultDouble,
    temp_max = nonDefaultDouble,
    temp_min = nonDefaultDouble,
    humidity = nonDefaultInt,
)

val testCityOverview = CityOverview (
    name= nonEmptyString,
    clouds=Clouds(default_int),
    coord=Coord(default_double,default_double),
    dt=default_int,
    id= nonDefaultInt,
    main= Main(default_double,default_int, nonDefaultInt,
        default_int,default_int, nonDefaultDouble, nonDefaultDouble, nonDefaultDouble),
    sys= Sys(nonEmptyString, default_int, default_int,default_int),
    visibility=default_int,
    weather=listOf(Weather(EMPTY_STRING,EMPTY_STRING,default_int,EMPTY_STRING)),
    wind=Wind(default_int,default_double)
)