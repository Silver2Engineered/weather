package com.example.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.appId
import com.example.weather.cityIds
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.StateDetails
import com.example.weather.network.StateOverview
import com.example.weather.network.WeatherApi
import com.example.weather.units
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepository (private val database: CityRoomDatabase) {

    private var _cityOverview= MutableLiveData<StateOverview>(StateOverview.Loading)
    val cityOverview: LiveData<StateOverview> = _cityOverview
    private var _cityDetails = MutableLiveData<StateDetails>(StateDetails.Loading)
    val cityDetails: LiveData<StateDetails> = _cityDetails

    suspend fun refreshCityOverview() {
        withContext(Dispatchers.IO) {
            try {
                val cityOverview = WeatherApi.retrofitService.getCities(
                    cityIds,
                    appId,
                    units
                ).list.map { it.toCachedModel() }
                database.cityOverviewDao.insert(cityOverview)
                _cityOverview.postValue(StateOverview.Success(
                    database.cityOverviewDao.getCities().map { it.toDomainModel()}))
            }
            catch (e: Exception) {
                _cityOverview.postValue(StateOverview.Error(e))
            }
        }
    }

    suspend fun refreshCityDetails(cityId: String) {
        withContext(Dispatchers.IO) {
            try {
                val cityDetails =
                    WeatherApi.retrofitService.getCityData(cityId, "fake", units).toCachedModel()
                database.cityDetailsDao.insert(cityDetails)
                _cityDetails.postValue(StateDetails.Success(
                    database.cityDetailsDao.getCityData(cityId.toInt()).toDomainModel()))
            }
            catch (e: Exception) {
                _cityDetails.postValue(StateDetails.Error(e))
            }
        }
    }
}