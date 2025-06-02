package com.erayerarslan.t_vac_kotlin.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erayerarslan.t_vac_kotlin.MainActivity
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.databinding.FragmentHomeBinding
import com.erayerarslan.t_vac_kotlin.model.Device
import com.erayerarslan.t_vac_kotlin.model.SensorDataManager
import com.erayerarslan.t_vac_kotlin.ui.adapter.HistoryAdapter
import com.erayerarslan.t_vac_kotlin.ui.adapter.ParameterAdapter
import com.erayerarslan.t_vac_kotlin.ui.device.DeviceUiState
import com.erayerarslan.t_vac_kotlin.ui.device.DeviceViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private val deviceViewModel by activityViewModels<DeviceViewModel>()

    private lateinit var parameterAdapter: ParameterAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var mapView: MapView

    private var deviceSelected: Boolean = false
    private var pairedDevice: Device? = null

    private val dateFormatter = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        (activity as MainActivity).showBottomNavigation()

        // MapView setup
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState?.getBundle("MapViewBundleKey"))

        // Observe the flag set by DeviceFragment
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle
            ?.getLiveData<Boolean>("device_selected")
            ?.observe(viewLifecycleOwner) { selected ->
                deviceSelected = selected
            }

        // History RecyclerView setup
        historyAdapter = HistoryAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = historyAdapter

        // Parameter RecyclerView setup
        parameterAdapter = ParameterAdapter(emptyList())
        binding.rvParameters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = parameterAdapter
        }

        // FAB -> navigate to DeviceFragment
        binding.fab.setOnClickListener {
            // reset flag (so if user comes back without selecting, deviceSelected remains false)
            homeViewModel.deviceSelected.value = false
            findNavController().navigate(R.id.action_homeFragment_to_deviceFragment)
        }

        // TabLayout listener
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedTab = when (tab.text) {
                    "Maps"    -> Tab.MAPS
                    "History" -> Tab.HISTORY
                    else      -> Tab.PARAMETERS
                }
                homeViewModel.onTabSelected(selectedTab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 1) HomeViewModel.uiState collection
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.Init -> {
                        binding.progressBar.isVisible = false
                    }
                    is HomeUiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is HomeUiState.Success -> {
                        // data is loaded, hide progress bar
                        binding.progressBar.isVisible = false
                        binding.rvParameters.isVisible = false

                        // show charts in Parameters tab
                        parameterAdapter.submitList(state.charts)

                        // update header texts
                        binding.titleText.text = "Soil Analysis"
                        binding.fieldZoneText.text = state.sensorData.locationName
                        binding.lastUpdatedText.text = "Last updated: ${dateFormatter.format(Date())}"
                    }
                    is HomeUiState.Error -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }

        // 2) HomeViewModel.selectedTab collection
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.selectedTab.collect { tab ->
                when (tab) {
                    Tab.PARAMETERS -> {
                        binding.parameterHeaderRow.isVisible = true
                        binding.rvParameters.isVisible = true
                        binding.rvHistory.isVisible = false
                        binding.mapView.isVisible = false
                    }
                    Tab.HISTORY -> {
                        binding.parameterHeaderRow.isVisible = false
                        binding.rvParameters.isVisible = false
                        binding.rvHistory.isVisible = true
                        binding.mapView.isVisible = false

                        // load history from cache
                        historyAdapter.submitList(SensorDataManager.getAll().reversed())
                    }
                    Tab.MAPS -> {
                        binding.parameterHeaderRow.isVisible = false
                        binding.rvParameters.isVisible = false
                        binding.rvHistory.isVisible = false
                        binding.mapView.isVisible = true
                    }
                }
            }
        }

        // 3) DeviceViewModel.uiState collection
        viewLifecycleOwner.lifecycleScope.launch {
            deviceViewModel.uiState.collect { state ->
                when (state) {
                    is DeviceUiState.DevicesLoaded -> {
                        pairedDevice = state.devices.find { it.isPaired }
                    }
                    is DeviceUiState.SensorDataLoaded -> {
                        homeViewModel.refreshSensorData()
                    }
                    is DeviceUiState.Error -> {
                        // no-op
                    }
                    else -> Unit
                }
            }
        }

        // 4) Refresh Data button
        binding.refreshDataButton.setOnClickListener {
            if (!deviceSelected) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Bluetooth Bağlantısı Gerekli")
                    .setMessage("Lütfen önce Bluetooth bağlantısını sağlayın.")
                    .setPositiveButton("Tamam", null)
                    .show()
            } else {
                // show progress indicator
                binding.progressBar.isVisible = true
                // hide parameters list while refreshing
                binding.rvParameters.isVisible = false

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(5000L)
                    // generate and add one new SensorData
                    SensorDataManager.InitialSensor(1)
                    // refresh HomeViewModel data
                    homeViewModel.refreshSensorData()
                    // update history tab cache
                    historyAdapter.submitList(SensorDataManager.getAll().reversed())

                    // hide progress, show parameters again
                    binding.progressBar.isVisible = false
                    binding.rvParameters.isVisible = true
                }
            }
        }

        // 5) Location updates for map
        binding.mapView.getMapAsync { googleMap ->
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
                val client = LocationServices.getFusedLocationProviderClient(requireActivity())
                val req = LocationRequest.create().apply {
                    interval = 5 * 60 * 1000L
                    fastestInterval = 2 * 60 * 1000L
                    priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                }
                client.requestLocationUpdates(
                    req,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            result.lastLocation?.let { loc ->
                                val city = android.location.Geocoder(
                                    requireContext(),
                                    Locale.getDefault()
                                ).getFromLocation(loc.latitude, loc.longitude, 1)
                                    ?.firstOrNull()?.adminArea ?: "Bilinmiyor"
                                homeViewModel.updateLocation(city)
                            }
                        }
                    },
                    Looper.getMainLooper()
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
