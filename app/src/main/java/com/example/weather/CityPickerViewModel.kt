package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.network.City
import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherApiResponse
import kotlinx.coroutines.launch

class CityPickerViewModel : ViewModel() {

    private val _cities = MutableLiveData<List<City>>()

    val cities: MutableLiveData<List<City>>
        get() = _cities

    init {
        getCities()
    }

    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cities] [List] [LiveData].
     */

    private fun getCities() {
        viewModelScope.launch {
            try {
                val response: WeatherApiResponse = WeatherApi.retrofitService.getCities()
               _cities.value = response.list
            } catch (e: Exception) {
                _cities.value = listOf()
            }
        }

    }
}