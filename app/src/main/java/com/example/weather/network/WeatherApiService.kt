package com.example.weather.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"


/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Log both request and response
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

/**
 * A public interface that exposes the [getCities] and [getCityData] methods
 */
interface WeatherApiService {
    @GET("data/2.5/group")
    suspend fun getCities(
        @Query("id") cityId: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ) : WeatherApiResponse

    @GET("data/2.5/weather")
    suspend fun getCityData(
        @Query("id") cityId: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ) : CityDetails
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object WeatherApi {
    val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}