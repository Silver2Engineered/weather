package com.example.weather.repository

import android.content.Context
import com.example.weather.appId
import com.example.weather.cityIds
import com.example.weather.network.CachedCityDetails
import com.example.weather.network.CachedCityOverview
import com.example.weather.network.CityDetails
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.WeatherApi
import com.example.weather.units
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepository (private val database: CityRoomDatabase) {

    suspend fun getCityOverview(): List<CachedCityOverview> {
        return WeatherApi.retrofitService.getCities(cityIds, appId, units).list.map{it.toCachedModel()}
    }

    suspend fun getCityDetails(cityId: Int): CachedCityDetails {
        return WeatherApi.retrofitService.getCityData(cityId.toString(), appId, units).toCachedModel()
    }


    suspend fun insertCityOverview() {
        withContext(Dispatchers.IO) {
            database.cityOverviewDao.insert(getCityOverview())
        }
    }

    suspend fun insertCityDetails(cityDetails: CityDetails) {
        withContext(Dispatchers.IO) {
            database.cityDetailsDao.insert(getCityDetails(cityDetails.id))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CityRepository? = null

        fun getInstance(context: Context): CityRepository {
            return INSTANCE ?: synchronized(this) {
                val database = CityRoomDatabase.getDatabase(context)
                INSTANCE ?: CityRepository(database).also { INSTANCE = it }
            }
        }
    }

}