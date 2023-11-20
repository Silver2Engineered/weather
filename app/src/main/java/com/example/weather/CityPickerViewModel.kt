package com.example.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityOverview
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherApiResponse
import com.example.weather.repository.CityRepository
import kotlinx.coroutines.launch

const val cityIds = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
const val appId = "bd4472d97ca0b479dc32513cf50a1bf3"
const val units = "metric"

class CityPickerViewModel(application: Application) : AndroidViewModel(application){

    private var _cities = MutableLiveData<List<CityOverview>>()
    var cities: LiveData<List<CityOverview>> = _cities
    private val cityRepository = CityRepository(CityRoomDatabase.getDatabase(application))
    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cities] [List] [LiveData].
     */

    fun getCityInfo() {
        viewModelScope.launch {
            try {
                val response: WeatherApiResponse = WeatherApi.retrofitService.getCities(cityIds, appId, units)
                cityRepository.insertCityOverview()
               _cities.value = response.list
            } catch (e: Exception) {
                _cities.value = listOf()
            }
        }
    }

    fun getCityOverviewFromDatabase(cityId: Int) {
        viewModelScope.launch {
            val cachedCityOverview = cityRepository.getCityOverview()
            _cities.value = cachedCityOverview?.map{it.toDomainModel()}
        }
    }
}