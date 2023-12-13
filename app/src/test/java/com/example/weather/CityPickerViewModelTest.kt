package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.CityPickerViewModel.Companion.convertCelsiusToFahrenheit
import com.example.weather.CityPickerViewModel.Companion.formatLowAndHigh
import com.example.weather.network.StateOverview
import com.example.weather.repository.CityRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test

class CityPickerViewModelTest {

    private val repository: CityRepository = mockk(relaxed = true)
    private lateinit var cityPickerViewModel: CityPickerViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `Get city overview works successfully`() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { repository.refreshCityOverview()} answers {
            every { repository.cityOverview.value } returns
                    StateOverview.Success(listOf(testCityOverview))
        }
        cityPickerViewModel = CityPickerViewModel.Factory(repository).create(CityPickerViewModel::class.java)
        cityPickerViewModel.getCityInfo()
        assert(cityPickerViewModel.cities.value is StateOverview.Success)
    }

    @Test
    fun `Temperature conversion works correctly`() {
        TestCase.assertEquals(convertCelsiusToFahrenheit(27.0), 80)
    }

    @Test
    fun `Formatter works correctly`() {
        TestCase.assertEquals(formatLowAndHigh(testCityOverview), "932℉/932℉")
    }
}