package com.farukayata.t_vac_kotlin.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farukayata.t_vac_kotlin.databinding.FragmentSearchBinding
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.farukayata.t_vac_kotlin.model.SensorDataManager


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        recyclerView = binding.treeRecyclerViewSearch
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = SearchAdapter(emptyList()) { selectedTree ->
            val action = SearchFragmentDirections.actionSearchFragmentToTreeDetailFragment(selectedTree)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        //sayfa açılınca lokal liste gelcek
        viewModel.loadInitialList()
        observeEvents()

        binding.searchButton.setOnClickListener {
            val name = binding.searchEditText.text.toString()
            viewModel.fetchTreeList(name)
            //ai modunu kapatcak flag gibi
            //viewModel.isAIActive.value = false
        }

        //her harf yazdığında veya sildiğinde fetchTreeList tetiklencek
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable.toString()
            viewModel.fetchTreeList(query)
        }

        binding.aiSuggestButton.setOnClickListener {
            val sensorData = SensorData(
                phValue = SensorDataManager.sensorData?.phValue ,
                temperatureValue = SensorDataManager.sensorData?.temperatureValue,
                humidityValue = SensorDataManager.sensorData?.humidityValue,
                conductibilityValue = SensorDataManager.sensorData?.conductibilityValue,
                fosforValue = SensorDataManager.sensorData?.fosforValue,
                potasyumValue = SensorDataManager.sensorData?.potasyumValue,
                azotValue = SensorDataManager.sensorData?.azotValue,
                locationName = SensorDataManager.sensorData?.locationName
            )
            viewModel.fetchTreeSuggestions(sensorData)
            Log.d("SearchResponse","$sensorData")
        }

    }

    /*-> daha basit ve sade kurduk
    private fun observeEvents() {
        // Lokal arama sonucu gözlemi (LiveData)
        viewModel.filteredTreeListSearch.observe(viewLifecycleOwner) { treeList ->
            lifecycleScope.launch {
                if (!viewModel.isAIActive.value) { // AI aktif değilse lokal listeyi gösterttik
                    if (treeList.isNotEmpty()) {
                        adapter.updateTreeList(treeList)
                    } else {
                        adapter.updateTreeList(emptyList())
                    }
                }
            }
        }

        // AI'dan gelen önerileri gözlemle (StateFlow)
        lifecycleScope.launchWhenStarted {
            viewModel.suggestedTrees.collect { aiTrees ->
                if (aiTrees.isNotEmpty()) {
                    adapter.updateTreeList(aiTrees)
                }
            }
        }
    }
    */

    private fun observeEvents() {
        viewModel.filteredTreeListSearch.observe(viewLifecycleOwner) { treeList ->
            adapter.updateTreeList(treeList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}