package com.farukayata.t_vac_kotlin.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farukayata.t_vac_kotlin.databinding.FragmentSearchBinding
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController


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

        adapter = SearchAdapter(viewModel.searchFilterList){ selectedTree ->
            val action = SearchFragmentDirections.actionSearchFragmentToTreeDetailFragment(selectedTree)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
        observeEvents()
        binding.searchButton.setOnClickListener {
            val name = binding.searchEditText.text.toString()
            viewModel.fetchTreeList(name)
            //ai modunu kapatcak flag gibi
            viewModel.isAIActive.value = false
        }

        binding.aiSuggestButton.setOnClickListener {
            val sensorData = SensorData(
                phValue = "6.5",
                temperatureValue = "22",
                humidityValue = "45",
                conductibilityValue = "1.2",
                fosforValue = "medium",
                potasyumValue = "high",
                azotValue = "low"
            )
            viewModel.fetchTreeSuggestions(sensorData)
        }

    }

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


    /*
    private fun observeEvents() {

        viewModel.filteredTreeListSearch.observe(viewLifecycleOwner) { tree ->
            if (tree.isNotEmpty()) {
                adapter.updateTreeList(tree)
            } else {
                adapter.updateTreeList(emptyList())
            }

        }
    }
    */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}