package com.example.weather.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityOverviewDao {

    @Query("SELECT * FROM city_overview_table where name = name")
    fun getCities(): List<CachedCityOverview>

    @Insert
    suspend fun insert(cachedCity: CachedCityOverview)

    @Query("DELETE FROM city_overview_table")
    suspend fun deleteAll()
}

@Dao
interface CityDetailsDao {

    @Query("SELECT * FROM city_details_table ORDER BY name ASC")
    fun getCityData(): List<CachedCityDetails>

    @Insert
    suspend fun insert(cachedCityData: CachedCityDetails)

    @Query("DELETE FROM city_details_table")
    suspend fun deleteAll()
}