package com.example.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.network.City
import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherApiResponse
import kotlinx.coroutines.launch

const val cityIds = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
const val appId = "bd4472d97ca0b479dc32513cf50a1bf3"
const val units = "metric"

class CityPickerViewModel : ViewModel() {

    private var _cities = MutableLiveData<List<City>>()

    val cities: LiveData<List<City>> = _cities

    init {
        getCityInfo()
    }

    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cities] [List] [LiveData].
     */

    private fun getCityInfo() {
        viewModelScope.launch {
            try {
                val response: WeatherApiResponse = WeatherApi.retrofitService.getCities(cityIds, appId, units)
                Log.d("hi17", response.toString())
               _cities.value = response.list
            } catch (e: Exception) {
                _cities.value = listOf()
            }
        }

    }
}