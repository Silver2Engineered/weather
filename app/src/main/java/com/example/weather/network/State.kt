package com.example.weather.network

sealed class StateOverview {
    object Loading : StateOverview()
    data class Success(val data: List<CityOverview>) : StateOverview()
    data class Error(val error: Exception) : StateOverview()
}

sealed class StateDetails {
    object Loading : StateDetails()
    data class Success(val data: CityDetails) : StateDetails()
    data class Error(val error: Exception) : StateDetails()
}