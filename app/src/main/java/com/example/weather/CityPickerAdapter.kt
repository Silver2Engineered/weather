package com.example.weather
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.network.City

/**
 * Adapter for the [RecyclerView] in [CityPickerFragment].
 */

class CityPickerAdapter(private val dataSet: MutableLiveData<List<City>>, private val context: Context?) :
    RecyclerView.Adapter<CityPickerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val country: TextView
        val temp: TextView
        val lowAndHigh: TextView
        val humidity: TextView

        init {
            // Define click listener for the ViewHolder's View
            name = view.findViewById(R.id.Name)
            country = view.findViewById(R.id.Country)
            temp = view.findViewById(R.id.Temp)
            lowAndHigh = view.findViewById(R.id.LowAndHigh)
            humidity = view.findViewById(R.id.Humidity)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.city_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val dataSetValues: List<City>? = dataSet.value
        viewHolder.name.text = dataSetValues?.get(position)?.name.toString()
        viewHolder.country.text = dataSetValues?.get(position)?.sys?.country.toString()
        viewHolder.temp.text = context?.resources?.getString(R.string.temp, convertKelvintoFahrenheit(dataSetValues?.get(position)?.main?.temp).toString())
        viewHolder.lowAndHigh.text = formatLowAndHigh(dataSetValues, position)
        viewHolder.humidity.text = context?.resources?.getString(R.string.humidity, dataSetValues?.get(position)?.main?.humidity.toString())
    }

    private fun formatLowAndHigh(dataSetValues: List<City>?, position: Int): String {
        val low = convertKelvintoFahrenheit(dataSetValues?.get(position)?.main?.temp_min).toString()
        val high = convertKelvintoFahrenheit(dataSetValues?.get(position)?.main?.temp_max).toString()
        return "$low\u2109/$high\u2109"
    }

    private fun convertKelvintoFahrenheit(temp: Double?): Int {
        return if (temp != null) {
            return ((temp - 273.15) * 9/5 + 32).toInt()
        } else 0
    }

    override fun getItemCount(): Int {
        val citiesList: List<City> = dataSet.value ?: emptyList()
        return citiesList.size
    }

}
