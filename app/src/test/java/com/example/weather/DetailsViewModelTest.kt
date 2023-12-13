package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.StateDetails
import com.example.weather.repository.CityRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    private val repository: CityRepository = mockk(relaxed = true)
    private lateinit var detailsViewModel: DetailsViewModel


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `Get city weather works successfully`() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { repository.refreshCityDetails(any())} answers {
            every { repository.cityDetails.value } returns
                StateDetails.Success(testCityDetails)
        }
        detailsViewModel = DetailsViewModel.Factory(repository).create(DetailsViewModel::class.java)
        detailsViewModel.getCityWeather("")
        assert(detailsViewModel.cityData.value is StateDetails.Success)
    }

    }