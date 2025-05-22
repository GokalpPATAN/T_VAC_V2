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

        adapter = SearchAdapter(emptyList()) { selectedPlant ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToTreeDetailFragment(selectedPlant)
            findNavController().navigate(action)
        }
        binding.treeRecyclerViewSearch.adapter = adapter

        viewModel.loadInitialList()
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


        /*
        binding.aiSuggestButton.setOnClickListener {
            try {
                val currentSensorData = SensorDataManager.getLatestSensorData()
                if (currentSensorData == null) {
                    Toast.makeText(requireContext(), "Sensör verisi bulunamadı veya süresi dolmuş", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Log.d("SearchFragment", "Kullanılan sensör verileri: $currentSensorData")
                viewModel.fetchPlantSuggestions()
            } catch (e: Exception) {
                Log.e("SearchFragment", "AI önerisi alınırken hata: ${e.message}")
                Toast.makeText(requireContext(), "AI önerisi alınamadı", Toast.LENGTH_SHORT).show()
            }
        }

         */
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










/*
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

        adapter = SearchAdapter(emptyList()) { selectedPlant ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToTreeDetailFragment(selectedPlant)
            findNavController().navigate(action)
        }
        binding.treeRecyclerViewSearch.adapter = adapter

        viewModel.loadInitialList()
        observeEvents()

        binding.searchButton.setOnClickListener {
            viewModel.fetchPlantList(binding.searchEditText.text.toString())
        }

        binding.searchEditText.addTextChangedListener { editable ->
            viewModel.fetchPlantList(editable.toString())
        }

        binding.aiSuggestButton.setOnClickListener {
            try {
                val currentSensorData = SensorDataManager.getLatestSensorData()
                if (currentSensorData == null) {
                    Toast.makeText(requireContext(), "Sensör verisi bulunamadı", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Log.d("SearchFragment", "Kullanılan sensör verileri: $currentSensorData")
                viewModel.fetchPlantSuggestions()
            } catch (e: Exception) {
                Log.e("SearchFragment", "Sensör verisi alınamadı", e)
                Toast.makeText(requireContext(), "Sensör verisi alınamadı", Toast.LENGTH_SHORT).show()
            }
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


 */









/*
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
import com.farukayata.t_vac_kotlin.databinding.FragmentSearchBinding
import com.farukayata.t_vac_kotlin.model.SensorData
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

        adapter = SearchAdapter(emptyList()) { selectedPlant ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToTreeDetailFragment(selectedPlant)
            findNavController().navigate(action)
        }
        binding.treeRecyclerViewSearch.adapter = adapter

        viewModel.loadInitialList()
        observeEvents()

        binding.searchButton.setOnClickListener {
            viewModel.fetchPlantList(binding.searchEditText.text.toString())
        }

        binding.searchEditText.addTextChangedListener { editable ->
            viewModel.fetchPlantList(editable.toString())
        }

        binding.aiSuggestButton.setOnClickListener {
            // Sensör verilerini doğrudan kullan
            val currentSensorData = SensorDataManager.getLatestSensorData()
            if (currentSensorData != null) {
                Log.d("SearchFragment", "Kullanılan sensör verileri: $currentSensorData")
                viewModel.fetchPlantSuggestions(currentSensorData)
            } else {
                Log.d("SearchFragment", "Sensör verisi bulunamadı, varsayılan veriler kullanılıyor")
                viewModel.fetchPlantSuggestions(SensorData())
            }
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

 */








/*
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
import com.farukayata.t_vac_kotlin.databinding.FragmentSearchBinding
import com.farukayata.t_vac_kotlin.model.SensorData
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
        // RecyclerView'i LinearLayoutManager ile başlat
        binding.treeRecyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter'ı Plant tipinde başlatıyoruz
        adapter = SearchAdapter(emptyList()) { selectedPlant ->
            // Seçilen Plant öğesini Detail ekranına gönder
            val action = SearchFragmentDirections
                .actionSearchFragmentToTreeDetailFragment(selectedPlant)
            findNavController().navigate(action)
        }
        binding.treeRecyclerViewSearch.adapter = adapter

        // İlk yüklemede lokal listeyi getir
        viewModel.loadInitialList()
        observeEvents()

        // Arama butonuna tıklayınca
        binding.searchButton.setOnClickListener {
            viewModel.fetchPlantList(binding.searchEditText.text.toString())
        }

        // Metin değiştikçe filtrele
        binding.searchEditText.addTextChangedListener { editable ->
            viewModel.fetchPlantList(editable.toString())
        }

        // AI öneri butonu
        binding.aiSuggestButton.setOnClickListener {
            val sensorData = SensorDataManager.sensorData?.let { sd ->
                SensorData(
                    phValue = sd.phValue,
                    temperatureValue = sd.temperatureValue,
                    humidityValue = sd.humidityValue,
                    conductibilityValue = sd.conductibilityValue,
                    fosforValue = sd.fosforValue,
                    potasyumValue = sd.potasyumValue,
                    azotValue = sd.azotValue,
                    locationName = sd.locationName
                )
            } ?: SensorData()
            viewModel.fetchPlantSuggestions(sensorData)
            Log.d("SearchResponse", sensorData.toString())
        }
    }

    private fun observeEvents() {
        // ViewModel'deki LiveData'yı gözle
        viewModel.filteredPlantList.observe(viewLifecycleOwner) { plantList ->
            adapter.updatePlantList(plantList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

 */
