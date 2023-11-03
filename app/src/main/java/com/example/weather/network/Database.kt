package com.example.weather.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities =
[
    CachedCityOverview::class,
    CachedCityDetails::class],
    version = 1, exportSchema = false)
public abstract class CityRoomDatabase : RoomDatabase() {

    abstract fun cityDao(): CityOverviewDao

    companion object {
        private var INSTANCE: CityRoomDatabase? = null

        fun getDatabase(context: Context): CityRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityRoomDatabase::class.java,
                    "city_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}