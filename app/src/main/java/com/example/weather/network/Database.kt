package com.example.weather.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities =
    [
        CachedCityOverview::class,
        CachedCityDetails::class
    ],
    version = 1, exportSchema = false)
public abstract class CityRoomDatabase : RoomDatabase() {

    abstract val cityOverviewDao: CityOverviewDao
    abstract val cityDetailsDao: CityDetailsDao
}

    private lateinit var INSTANCE: CityRoomDatabase

        fun getDatabase(context: Context): CityRoomDatabase {
            synchronized(CityRoomDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CityRoomDatabase::class.java,
                        "videos").build()
                }
            }
            return INSTANCE
        }