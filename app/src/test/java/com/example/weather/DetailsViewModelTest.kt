package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.StateDetails
import com.example.weather.repository.CityRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    private val repository: CityRepository = mockk(relaxed = true)
    private val database = mockk<CityRoomDatabase>(relaxed=true)
    private lateinit var detailsViewModel: DetailsViewModel


    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        detailsViewModel = DetailsViewModel.Factory(repository).create(DetailsViewModel::class.java)
    }

    @Test
    fun `Get city weather works successfully`() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { repository.refreshCityDetails(any())} returns mockk {
            coEvery { repository.cityDetails } returns MutableLiveData(
                StateDetails.Success(
                    testCityDetails
                )
            )
        }
        detailsViewModel.getCityWeather("500")
        Thread.sleep(5000)
        TestCase.assertEquals(detailsViewModel.cityData.value, StateDetails.Success(testCityDetails))
    }

    }