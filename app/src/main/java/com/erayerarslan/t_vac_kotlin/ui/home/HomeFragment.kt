package com.farukayata.t_vac_kotlin.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farukayata.t_vac_kotlin.R
import com.farukayata.t_vac_kotlin.databinding.FragmentHomeBinding
import com.farukayata.t_vac_kotlin.model.Device
import com.farukayata.t_vac_kotlin.ui.adapter.ParameterAdapter
import com.farukayata.t_vac_kotlin.ui.device.DeviceUiState
import com.farukayata.t_vac_kotlin.ui.device.DeviceViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel   by viewModels<HomeViewModel>()
    private val deviceViewModel by activityViewModels<DeviceViewModel>()

    private lateinit var adapter: ParameterAdapter
    private lateinit var mapView: MapView

    private var pairedDevice: Device? = null

    private val dateFormatter = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)

        // MapView setup
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState?.getBundle("MapViewBundleKey"))

        // RecyclerView
        adapter = ParameterAdapter(emptyList())
        binding.rvParameters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        // FAB -> DeviceFragment
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_deviceFragment)
        }

        // TabLayout listener
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val t = if (tab.text == "Maps") Tab.MAPS else Tab.PARAMETERS
                homeViewModel.onTabSelected(t)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // 1) HomeViewModel.uiState toplama
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.Init    -> { binding.progressBar.isVisible = false }
                    is HomeUiState.Loading -> { binding.progressBar.isVisible = true }
                    is HomeUiState.Success -> {
                        binding.progressBar.isVisible = false
                        // Charts
                        adapter.submitList(state.charts)
                        // Header
                        binding.titleText.text       = "Soil Analysis"
                        binding.fieldZoneText.text   = state.sensorData.locationName
                        binding.lastUpdatedText.text =
                            "Last updated: ${dateFormatter.format(Date())}"
                    }
                    is HomeUiState.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(),
                            state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // 2) HomeViewModel.selectedTab toplama
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.selectedTab.collect { tab ->
                val showParams = tab == Tab.PARAMETERS
                binding.rvParameters.isVisible       = showParams
                binding.parameterHeaderRow.isVisible = showParams
                binding.mapView.isVisible            = !showParams
            }
        }

        // 3) DeviceViewModel.uiState toplama
        viewLifecycleOwner.lifecycleScope.launch {
            deviceViewModel.uiState.collect { state ->
                when (state) {
                    is DeviceUiState.DevicesLoaded -> {
                        // eşleşmiş cihazı Device nesnesi olarak tut
                        pairedDevice = state.devices.find { it.isPaired }
                    }
                    is DeviceUiState.SensorDataLoaded -> {
                        homeViewModel.refreshSensorData()
                    }
                    is DeviceUiState.Error -> {
                        Toast.makeText(requireContext(),
                            state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        // 4) Refresh butonu
        binding.refreshDataButton.setOnClickListener {
            pairedDevice?.let { device ->
                deviceViewModel.pairAndFetch(device)
            } ?: Toast.makeText(
                requireContext(),
                "Bağlı cihaz bulunamadı!",
                Toast.LENGTH_SHORT
            ).show()
        }

        // 5) Konum izleme
        binding.mapView.getMapAsync { googleMap ->
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
                val client = LocationServices
                    .getFusedLocationProviderClient(requireActivity())
                val req = LocationRequest.create().apply {
                    interval        = 5 * 60 * 1000L
                    fastestInterval = 2 * 60 * 1000L
                    priority        = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                }
                client.requestLocationUpdates(req,
                    object: LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            result.lastLocation?.let { loc ->
                                val city = android.location.Geocoder(
                                    requireContext(), Locale.getDefault()
                                ).getFromLocation(loc.latitude, loc.longitude, 1)
                                    ?.firstOrNull()?.adminArea ?: "Bilinmiyor"
                                homeViewModel.updateLocation(city)
                            }
                        }
                    }, Looper.getMainLooper()
                )
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1001
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        _binding = null
    }
}
