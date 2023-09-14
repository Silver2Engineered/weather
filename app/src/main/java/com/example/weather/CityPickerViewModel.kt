package com.example.weather

import androidx.lifecycle.ViewModel

class CityPickerViewModel : ViewModel() {

    private val _cities: List<Int> = (1..100).toList()
    val cities: List<Int>
    get() = _cities

}