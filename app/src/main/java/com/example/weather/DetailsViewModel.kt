package com.example.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityDetails
import com.example.weather.network.CityRoomDatabase.Companion.getDatabase
import com.example.weather.repository.CityRepository
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val cityRepository = CityRepository(getDatabase(application))
    private var _cityData = cityRepository.cityDetails
    var cityData: MutableLiveData<CityDetails> = _cityData

    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cityData] [LiveData].
     */

    fun getCityWeather(cityId: String) {
        viewModelScope.launch {
            cityRepository.refreshCityDetails(cityId)
        }

    }
}