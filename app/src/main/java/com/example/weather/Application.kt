package com.example.weather

import android.app.Application
import com.example.weather.network.CityRoomDatabase
import com.example.weather.repository.CityRepository

class Application : Application(){
    val database: CityRoomDatabase by lazy { CityRoomDatabase.getDatabase(this) }
    val repository: CityRepository by lazy { CityRepository(database) }
}