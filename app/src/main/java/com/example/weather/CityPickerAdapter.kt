package com.example.weather
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.CityPickerViewModel.Companion.convertCelsiusToFahrenheit
import com.example.weather.CityPickerViewModel.Companion.formatLowAndHigh
import com.example.weather.databinding.CityItemBinding
import com.example.weather.network.CityOverview

/**
 * Adapter for the [RecyclerView] in [CityPickerFragment].
 */

class CityPickerAdapter(private val context: Context?, private var dataSet: List<CityOverview> = listOf()) :
    RecyclerView.Adapter<CityPickerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(val binding: CityItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

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
        val data: CityOverview? = dataSet.get(position)
        viewHolder.binding.name.text = data?.name.toString()
        viewHolder.binding.country.text = data?.sys?.country.toString()
        viewHolder.binding.temp.text = context?.resources?.getString(R.string.temp, convertCelsiusToFahrenheit(data?.main?.temp).toString())
        viewHolder.binding.lowAndHigh.text = formatLowAndHigh(data)
        viewHolder.binding.humidity.text = context?.resources?.getString(R.string.humidity, data?.main?.humidity.toString())
        viewHolder.binding.card.setOnClickListener {
            val action = CityPickerFragmentDirections.actionCityPickerFragmentToDetailsFragment(city = data?.id.toString())
            viewHolder.binding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateCityData(cities: List<CityOverview>) {
        dataSet = cities
        notifyDataSetChanged()
    }

}
