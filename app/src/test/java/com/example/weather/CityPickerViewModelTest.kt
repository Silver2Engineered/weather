package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.StateOverview
import com.example.weather.repository.CityRepository
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CityPickerViewModelTest {

    private lateinit var repository: CityRepository
    private val database = mockk<CityRoomDatabase>(relaxed=true)
    private lateinit var cityPickerViewModel: CityPickerViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        repository = CityRepository(database)
    }

    @Test
    fun `Get city overview works successfully`() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val cityPickerViewModel = CityPickerViewModel(repository)
        database.cityOverviewDao.insert(listOf(testCachedCityOverview))
        cityPickerViewModel.getCityInfo()
        Thread.sleep(5000)
        TestCase.assertEquals(cityPickerViewModel.cities.value, StateOverview.Success(database.cityOverviewDao.getCities().map { it.toDomainModel() }))
    }
}