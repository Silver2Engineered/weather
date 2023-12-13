package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weather.network.CityDetails
import com.example.weather.network.StateDetails
import com.example.weather.repository.CityRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailsViewModel(private val repository: CityRepository) : ViewModel() {

    private var _cityData = repository.cityDetails
    val cityData: LiveData<StateDetails> = _cityData

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

    companion object {

        fun formatLowAndHighDetails(it: CityDetails?): String {
            val low = convertCelsiusToFahrenheit(it?.main?.temp_min).toString()
            val high = convertCelsiusToFahrenheit(it?.main?.temp_max).toString()
            return "$low\u2109/$high\u2109"
        }

        fun convertCelsiusToFahrenheit(temp: Double?): Int {
            return if (temp != null) {
                return (temp * 9 / 5 + 32).toInt()
            } else 0
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertTime(timestamp: Int?, timeZoneOffset: Int?): String {
            val offset = ZoneOffset.ofTotalSeconds(timeZoneOffset!!)
            val instant = Instant.ofEpochSecond(timestamp!!.toLong())
            val formatter = DateTimeFormatter.ofPattern("K:mm a", Locale.ENGLISH)
            return instant.atOffset(offset).format(formatter)
        }
    }
}