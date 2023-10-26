package com.example.weather.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    fun getCities(): List<CachedCity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cachedCity: CachedCity)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}

@Dao
interface CityDataDao {

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    fun getCityData(): List<CachedCityData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cachedCityData: CachedCityData)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}