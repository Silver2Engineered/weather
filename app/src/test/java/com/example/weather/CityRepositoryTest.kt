package com.example.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather.network.CityRoomDatabase
import com.example.weather.repository.CityRepository
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit


@RunWith(AndroidJUnit4::class)
class CityRepositoryTest {
    private val mockDb = mockk<CityRoomDatabase>(relaxed=true)
    private val mockRetrofit = mockk<Retrofit>(relaxed=true)
    private val mockRepository = CityRepository(mockDb)
    //instantiate and make sure the states are correct
    @Test
    fun cityOverview_success() {

    }
    }
