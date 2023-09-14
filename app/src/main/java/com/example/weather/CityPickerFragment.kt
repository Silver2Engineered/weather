package com.example.weather

import CityPickerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FragmentCityPickerBinding

class CityPickerFragment : Fragment() {

    private lateinit var viewModel: CityPickerViewModel
    private lateinit var recyclerView: RecyclerView
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

        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

        recyclerView = binding.recyclerView

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CityPickerAdapter(viewModel.cities, context)


    }

}