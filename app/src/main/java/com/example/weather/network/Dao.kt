package com.example.weather.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    fun getCities(): List<CachedCityOverview>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cachedCity: CachedCityOverview)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}

@Dao
interface CityDataDao {

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    fun getCityData(): List<CachedCityDetails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cachedCityData: CachedCityDetails)

    @Query("DELETE FROM city_table")
    suspend fun deleteAll()
}