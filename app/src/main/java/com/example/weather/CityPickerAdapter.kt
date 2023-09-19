package com.example.weather
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.CityItemBinding
import com.example.weather.network.City

/**
 * Adapter for the [RecyclerView] in [CityPickerFragment].
 */

class CityPickerAdapter(private val dataSet: LiveData<List<City>>, private val context: Context?) :
    RecyclerView.Adapter<CityPickerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(val binding: CityItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val dataSetValues: List<City>? = dataSet.value
        viewHolder.binding.name.text = dataSetValues?.get(position)?.name.toString()
        viewHolder.binding.country.text = dataSetValues?.get(position)?.sys?.country.toString()
        viewHolder.binding.temp.text = context?.resources?.getString(R.string.temp, convertKelvinToFahrenheit(dataSetValues?.get(position)?.main?.temp).toString())
        viewHolder.binding.lowAndHigh.text = formatLowAndHigh(dataSetValues, position)
        viewHolder.binding.humidity.text = context?.resources?.getString(R.string.humidity, dataSetValues?.get(position)?.main?.humidity.toString())
    }

    private fun formatLowAndHigh(dataSetValues: List<City>?, position: Int): String {
        val low = convertKelvinToFahrenheit(dataSetValues?.get(position)?.main?.temp_min).toString()
        val high = convertKelvinToFahrenheit(dataSetValues?.get(position)?.main?.temp_max).toString()
        return "$low\u2109/$high\u2109"
    }

    private fun convertKelvinToFahrenheit(temp: Double?): Int {
        return if (temp != null) {
            return ((temp - 273.15) * 9/5 + 32).toInt()
        } else 0
    }

    override fun getItemCount(): Int {
        val citiesList: List<City> = dataSet.value ?: emptyList()
        return citiesList.size
    }

}
