package com.example.weather.network

import androidx.room.Dao
import androidx.room.Insert
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

    @Query("SELECT * FROM city_details_table")
    fun getCityData(): List<CachedCityDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cachedCityData: CachedCityDetails, cityId: String)

    @Query("DELETE FROM city_details_table")
    suspend fun deleteAll()
}