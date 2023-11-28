package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityDetails
import com.example.weather.repository.CityRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: CityRepository) : ViewModel() {

    private var _cityData = repository.cityDetails
    var cityData: MutableLiveData<CityDetails> = _cityData

    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cityData] [LiveData].
     */

    fun getCityWeather(cityId: String) {
        viewModelScope.launch {
            repository.refreshCityDetails(cityId)
        }

    }

    class Factory(private val repository: CityRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}