package com.example.weather

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.weather.databinding.FragmentDetailsBinding
import com.example.weather.network.CityDetails
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

internal const val base_url = "https://openweathermap.org/img/wn/"
internal const val url_suffix = "@2x.png"
class DetailsFragment : Fragment() {

    private lateinit var cityId: String
    private val viewModel: DetailsViewModel by viewModels()

    companion object {
        val CITY = "city"
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            cityId = it.getString(CITY).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cityData.observe(viewLifecycleOwner) {
            updateBindings(binding, it)
        }
        viewModel.getCityWeather(cityId)
    }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatLowAndHighDetails(it: CityDetails?): String {
        val low = convertCelsiusToFahrenheit(it?.main?.temp_min).toString()
        val high = convertCelsiusToFahrenheit(it?.main?.temp_max).toString()
        return "$low\u2109/$high\u2109"
    }

    private fun convertCelsiusToFahrenheit(temp: Double?): Int {
        return if (temp != null) {
            return (temp * 9/5 + 32).toInt()
        } else 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(timestamp: Int?, timeZoneOffset: Int?): String {
        val offset = ZoneOffset.ofTotalSeconds(timeZoneOffset!!)
        val instant = Instant.ofEpochSecond(timestamp!!.toLong())
        val formatter = DateTimeFormatter.ofPattern("K:mm a", Locale.ENGLISH)
        return instant.atOffset(offset).format(formatter)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateBindings(binding: FragmentDetailsBinding, it: CityDetails?) {
        binding.name.text = it?.name.toString()
        binding.country.text = it?.sys?.country.toString()
        binding.temp.text = convertCelsiusToFahrenheit(it?.main?.temp).toString() + "℉"
        binding.lowAndHigh.text = formatLowAndHighDetails(it)
        binding.humidity.text = it?.main?.humidity.toString() + "%"
        binding.windspeedAmount.text = it?.wind?.speed.toString() + " m/s"
        binding.pressureAmount.text = it?.main?.pressure.toString() + " hPa"
        binding.description.text = it!!.weather[0].description[0].toUpperCase() + it!!.weather[0].description.substring(1)
        binding.sunrise.text = convertTime(it?.sys?.sunrise, it?.timezone)
        binding.sunset.text = convertTime(it?.sys?.sunset, it?.timezone)
        Glide.with(this)
            .load(base_url + it!!.weather[0].icon + url_suffix)
            .centerCrop()
            .into(binding.weatherIcon)
    }
}