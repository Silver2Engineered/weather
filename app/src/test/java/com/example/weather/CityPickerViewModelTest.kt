package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.network.CityRoomDatabase
import com.example.weather.repository.CityRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

class CityPickerViewModelTest {

    private lateinit var repository: CityRepository
    private val database = mockk<CityRoomDatabase>(relaxed=true)

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        repository = CityRepository(database)
    }

//    @Test
//    fun `Get city overview works successfully`() = runTest {
//        Dispatchers.setMain(Dispatchers.Unconfined)
//        val cityPickerViewModel = CityPickerViewModel(repository)
//        database.cityDetailsDao.insert(testCachedCityDetails)
//        detailsViewModel.getCityWeather("500")
//        Thread.sleep(5000)
//        TestCase.assertEquals(detailsViewModel.cityData.value, StateDetails.Success(database.cityDetailsDao.getCityData(500).toDomainModel()))
//    }
}