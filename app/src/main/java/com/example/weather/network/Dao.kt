package com.example.weather.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityOverviewDao {

    @Query("SELECT * FROM city_overview_table")
    fun getCities(): List<CachedCityOverview>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cachedCity: CachedCityOverview)

    @Query("DELETE FROM city_overview_table")
    suspend fun deleteAll()
}

@Dao
interface CityDetailsDao {

    @Query("SELECT * FROM city_details_table WHERE cityId = :cityId")
    fun getCityData(cityId: Int): List<CachedCityDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cachedCityData: CachedCityDetails)

    @Query("DELETE FROM city_details_table")
    suspend fun deleteAll()
}