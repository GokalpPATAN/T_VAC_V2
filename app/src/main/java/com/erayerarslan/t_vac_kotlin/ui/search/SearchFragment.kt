package com.farukayata.t_vac_kotlin.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.farukayata.t_vac_kotlin.databinding.FragmentSearchBinding
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import com.farukayata.t_vac_kotlin.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.treeRecyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter(emptyList(),viewModel) { selectedPlant ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToTreeDetailFragment(selectedPlant)
            findNavController().navigate(action)
        }
        binding.treeRecyclerViewSearch.adapter = adapter

        if (viewModel.filteredPlantList.value.isNullOrEmpty()) {
            viewModel.loadInitialList()
        }
        observeEvents()

        binding.searchButton.setOnClickListener {
            viewModel.fetchPlantList(binding.searchEditText.text.toString())
        }

        binding.searchEditText.addTextChangedListener { editable ->
            viewModel.fetchPlantList(editable.toString())
        }



        binding.aiSuggestButton.setOnClickListener {
            val currentSensorData = SensorDataManager.getLatestSensorData()
            if (currentSensorData == null) {
                Toast.makeText(requireContext(), "Sensör verisi bulunamadı veya süresi dolmuş", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("SearchFragment", "Kullanılan sensör verileri: $currentSensorData")
            viewModel.fetchPlantSuggestions(currentSensorData) // 🔁 GÜNCELLENDİ
        }
    }

    private fun observeEvents() {
        viewModel.filteredPlantList.observe(viewLifecycleOwner) { plantList ->
            adapter.updatePlantList(plantList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}