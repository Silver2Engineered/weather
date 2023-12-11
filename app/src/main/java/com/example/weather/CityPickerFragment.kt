package com.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FragmentCityPickerBinding
import com.example.weather.network.CityOverview
import com.example.weather.network.StateOverview

class CityPickerFragment : Fragment() {

    private val viewModel: CityPickerViewModel by viewModels {
        CityPickerViewModel.Factory((activity?.application as Application).repository)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var cityPickerAdapter: CityPickerAdapter
    private var _binding: FragmentCityPickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        cityPickerAdapter = CityPickerAdapter(context)
        recyclerView.adapter = cityPickerAdapter
        viewModel.cities.observe(viewLifecycleOwner) {
            when(it) {
                is StateOverview.Error -> displayOverviewError()
                is StateOverview.Loading -> displayOverviewLoading()
                is StateOverview.Success -> displayOverviewSuccess(it.data)
            }
        }
        viewModel.getCityInfo()
    }

    private fun displayOverviewError() {
        binding.recyclerView.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.errorMessage.text = getString(R.string.error_message)
        binding.errorMessage.visibility = View.VISIBLE
        binding.snag.visibility = View.VISIBLE
    }

    private fun displayOverviewLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
    }

    private fun displayOverviewSuccess(cities: List<CityOverview>) {
        cityPickerAdapter.updateCityData(cities)
        binding.recyclerView.visibility = View.VISIBLE
        binding.loading.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE


    }

}