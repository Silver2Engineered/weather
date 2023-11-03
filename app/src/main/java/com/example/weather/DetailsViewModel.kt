package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityDetails
import com.example.weather.network.WeatherApi
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private var _cityData = MutableLiveData<CityDetails?>()

    var cityData: MutableLiveData<CityDetails?> = _cityData
    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cityData] [LiveData].
     */

    fun getCityWeather(cityId: String) {
        viewModelScope.launch {
            try {
                val response: CityDetails = WeatherApi.retrofitService.getCityData(cityId, appId, units)
                _cityData.value = response
            } catch (e: Exception) {
                _cityData.value = null
            }
        }

    }
}