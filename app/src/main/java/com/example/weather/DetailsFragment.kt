package com.example.weather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.weather.databinding.FragmentDetailsBinding
import com.example.weather.network.CityDetails
import com.example.weather.network.EMPTY_STRING
import com.example.weather.network.StateDetails
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

internal const val base_url = "https://openweathermap.org/img/wn/"
internal const val url_suffix = "@2x.png"
class DetailsFragment : Fragment() {

    private lateinit var cityId: String
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory((activity?.application as Application).repository)
    }

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
            when(it) {
                is StateDetails.Error -> displayDetailsError()
                is StateDetails.Loading -> displayDetailsLoading()
                is StateDetails.Success -> displayDetailsSuccess(binding, it.data)
            }
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


    private fun displayDetailsError() {
        binding.name.text = EMPTY_STRING
        binding.country.text = EMPTY_STRING
        binding.temp.text = EMPTY_STRING
        binding.lowAndHigh.text = EMPTY_STRING
        binding.humidity.text = EMPTY_STRING
        binding.windspeedAmount.text = EMPTY_STRING
        binding.pressureAmount.text = EMPTY_STRING
        binding.description.text = EMPTY_STRING
        binding.sunrise.text = EMPTY_STRING
        binding.sunset.text = EMPTY_STRING
        binding.humidityIcon.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.firstLine.visibility = View.GONE
        binding.secondLine.visibility = View.GONE
        binding.snag.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = getString(R.string.error_message)
    }

    private fun displayDetailsLoading() {
        binding.name.text = EMPTY_STRING
        binding.country.text = EMPTY_STRING
        binding.temp.text = EMPTY_STRING
        binding.lowAndHigh.text = EMPTY_STRING
        binding.humidity.text = EMPTY_STRING
        binding.windspeedAmount.text = EMPTY_STRING
        binding.pressureAmount.text = EMPTY_STRING
        binding.description.text = EMPTY_STRING
        binding.sunrise.text = EMPTY_STRING
        binding.sunset.text = EMPTY_STRING
        binding.windspeedLabel.text = EMPTY_STRING
        binding.pressureLabel.text = EMPTY_STRING
        binding.sunriseLabel.text = EMPTY_STRING
        binding.sunsetLabel.text = EMPTY_STRING
        binding.humidityIcon.visibility = View.GONE
        binding.firstLine.visibility = View.GONE
        binding.secondLine.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
        binding.snag.visibility = View.GONE

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayDetailsSuccess(binding: FragmentDetailsBinding, it: CityDetails?) {
        binding.loading.visibility = View.GONE
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
        binding.windspeedLabel.text = getString(R.string.windspeed)
        binding.pressureLabel.text = getString(R.string.pressure)
        binding.sunriseLabel.text = getString(R.string.sunrise)
        binding.sunsetLabel.text = getString(R.string.sunset)
        binding.humidityIcon.visibility = View.VISIBLE
        binding.firstLine.visibility = View.VISIBLE
        binding.secondLine.visibility = View.VISIBLE
        binding.snag.visibility = View.GONE
        Glide.with(this)
            .load(base_url + it!!.weather[0].icon + url_suffix)
            .centerCrop()
            .into(binding.weatherIcon)
    }
}