package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityOverview
import com.example.weather.repository.CityRepository
import kotlinx.coroutines.launch

const val cityIds = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
const val appId = "bd4472d97ca0b479dc32513cf50a1bf3"
const val units = "metric"

class CityPickerViewModel(private val repository: CityRepository) : ViewModel() {

    private val _cities = repository.cityOverview
    val cities: LiveData<List<CityOverview>> = _cities
    /**
     * Gets city information from the Weather API Retrofit service and updates the
     * [cities] [List] [LiveData].
     */

    fun getCityInfo() {
        viewModelScope.launch {
            repository.refreshCityOverview()
        }
    }

    class Factory(val repository: CityRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CityPickerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CityPickerViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}