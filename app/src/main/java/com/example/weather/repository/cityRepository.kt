package com.example.weather.repository

import com.example.weather.appId
import com.example.weather.cityIds
import com.example.weather.network.CityDetails
import com.example.weather.network.CityOverview
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.WeatherApi
import com.example.weather.units
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class cityRepository (private val database: CityRoomDatabase) {

    suspend fun refreshCityOverview() {
        withContext(Dispatchers.IO) {
            val cityOverview: List<CityOverview> = WeatherApi.retrofitService.getCities(cityIds, appId, units).list
            database.cityOverviewDao.insert(cityOverview.map{it.toCachedModel()})
        }
    }

    suspend fun refreshCityDetails() {
        withContext(Dispatchers.IO) {
            val cityDetails: CityDetails = WeatherApi.retrofitService.getCityData(cityIds, appId, units)
            database.cityDetailsDao.insert(cityDetails.toCachedModel())
        }
    }

}