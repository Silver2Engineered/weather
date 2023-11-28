package com.example.weather.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CachedCityOverview::class, CachedCityDetails::class], version = 1, exportSchema = false)
abstract class CityRoomDatabase : RoomDatabase() {
    abstract val cityOverviewDao: CityOverviewDao
    abstract val cityDetailsDao: CityDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: CityRoomDatabase? = null

        fun getDatabase(context: Context): CityRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityRoomDatabase::class.java,
                    "city_database"
                )
                    .fallbackToDestructiveMigration() // This handles schema changes by recreating the database
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
