package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.network.CityRoomDatabase
import com.example.weather.network.StateDetails
import com.example.weather.network.StateOverview
import com.example.weather.network.WeatherApi
import com.example.weather.network.WeatherApiResponse
import com.example.weather.repository.CityRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CityRepositoryTest {
    private val database = mockk<CityRoomDatabase>(relaxed=true)
    private lateinit var repository: CityRepository
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Before
    fun setup() {
        mockkObject(WeatherApi)
        repository = CityRepository(database)
        coEvery { database.cityOverviewDao.insert(any())} just Runs
        coEvery { database.cityDetailsDao.insert(any())} just Runs
    }
    @Test
    fun `City Overview returns a success and inserts the data into the DB`() = runBlocking {
        coEvery { WeatherApi.retrofitService.getCities(any(), any(), any()) } returns WeatherApiResponse(1, listOf())
        repository.refreshCityOverview()
        assert(repository.cityOverview.value is StateOverview.Success)
    }

    @Test
    fun `City overview fails to fetch data from the API but succeeds from the database`()= runBlocking() {
        coEvery { WeatherApi.retrofitService.getCities(any(), any(), any()) } throws Exception("test error")
        coEvery { database.cityOverviewDao.getCities()} returns listOf(testCachedCityOverview)
        repository.refreshCityOverview()
        assert(repository.cityOverview.value is StateOverview.Success)
    }

    @Test
    fun `City Overview fails to fetch data from both the API and the database`() = runBlocking {
        coEvery { WeatherApi.retrofitService.getCities(any(), any(), any()) } throws Exception("test error")
        coEvery { database.cityOverviewDao.getCities()} throws Exception("test error")
        repository.refreshCityOverview()
        assert(repository.cityOverview.value is StateOverview.Error)
    }


    @Test
    fun `City Overview is in the loading state`() {
        assert(repository.cityOverview.value is StateOverview.Loading)
    }

    @Test
    fun `City details returns a success and inserts the data into the DB`() = runBlocking {
        coEvery { WeatherApi.retrofitService.getCityData(any(), any(), any()) } returns testCityDetails
        repository.refreshCityDetails("1")
        assert(repository.cityDetails.value is StateDetails.Success)
    }

    @Test
    fun `City details fails to fetch data from the API but succeeds from the database`() = runBlocking {
        coEvery { WeatherApi.retrofitService.getCityData(any(), any(), any()) } throws Exception("test error")
        coEvery { database.cityDetailsDao.getCityData(1)} returns testCachedCityDetails
        repository.refreshCityDetails("1")
        assert(repository.cityDetails.value is StateDetails.Success)
    }

    @Test
    fun `City details fails to fetch data from both the API and the database`() = runBlocking {
        coEvery { WeatherApi.retrofitService.getCityData(any(), any(), any()) } throws Exception("test error")
        coEvery { database.cityDetailsDao.getCityData(1)} throws Exception("test error")
        repository.refreshCityDetails("1")
        assert(repository.cityDetails.value is StateDetails.Error)
    }


    @Test
    fun `City details is in the loading state`() {
        assert(repository.cityDetails.value is StateDetails.Loading)
    }

}
