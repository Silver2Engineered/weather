package com.example.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityDetails
import com.example.weather.network.CityRoomDatabase.Companion.getDatabase
import com.example.weather.network.WeatherApi
import com.example.weather.repository.CityRepository
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private var _cityData = MutableLiveData<CityDetails?>()
    var cityData: MutableLiveData<CityDetails?> = _cityData
    private val cityRepository = CityRepository(getDatabase(application))

    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cityData] [LiveData].
     */

    fun getCityWeather(cityId: String) {
        viewModelScope.launch {
            try {
                val response: CityDetails = WeatherApi.retrofitService.getCityData(cityId, appId, units)
                cityRepository.insertCityDetails(response)
                _cityData.value = response
            } catch (e: Exception) {
                _cityData.value = null
            }
        }

    }

    fun getCityDetailsFromDatabase(cityId: Int) {
        viewModelScope.launch {
            val cachedCityDetails = cityRepository.getCityDetails(cityId)
            _cityData.value = cachedCityDetails?.toDomainModel()
        }
    }
}