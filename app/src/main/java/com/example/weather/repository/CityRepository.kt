package com.example.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.appId
import com.example.weather.cityIds
import com.example.weather.network.CityDetails
import com.example.weather.network.CityOverview
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.WeatherApi
import com.example.weather.units
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepository (private val database: CityRoomDatabase) {

    private var _cityOverview = MutableLiveData<List<CityOverview>>()
    val cityOverview: LiveData<List<CityOverview>> = _cityOverview
    private var _cityDetails = MutableLiveData<CityDetails>()
    val cityDetails: LiveData<CityDetails> = _cityDetails

    suspend fun refreshCityOverview() {
        withContext(Dispatchers.IO) {
            try {
                val cityOverview = WeatherApi.retrofitService.getCities(
                    cityIds,
                    appId,
                    units
                ).list.map { it.toCachedModel() }
                database.cityOverviewDao.insert(cityOverview)
            }
            catch (e: Exception) {

            }
            _cityOverview.postValue(database.cityOverviewDao.getCities().map{it.toDomainModel()})
        }
    }

    suspend fun refreshCityDetails(cityId: String) {
        withContext(Dispatchers.IO) {
            try {
                val cityDetails =
                    WeatherApi.retrofitService.getCityData(cityId, appId, units).toCachedModel()
                database.cityDetailsDao.insert(cityDetails)
            }
            catch (e: Exception) {

            }
            _cityDetails.postValue(database.cityDetailsDao.getCityData(cityId.toInt()).toDomainModel())
        }
    }
}