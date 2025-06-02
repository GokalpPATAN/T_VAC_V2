package com.erayerarslan.t_vac_kotlin.ui.device

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.databinding.FragmentDeviceBinding
import com.erayerarslan.t_vac_kotlin.ui.adapter.DeviceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeviceFragment : Fragment(R.layout.fragment_device) {

    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DeviceViewModel>()
    private lateinit var adapter: DeviceAdapter

    private val requestPerm = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.values.all { it }) {
            startDiscovery()
        } else {
            Toast.makeText(requireContext(), "Bluetooth taraması için gerekli izinler reddedildi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDeviceBinding.bind(view)

        adapter = DeviceAdapter(emptyList()) { device ->
            viewModel.pairAndFetch(device)
        }
        binding.deviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DeviceFragment.adapter
        }

        binding.btnStartDiscovery.setOnClickListener { checkAndStartDiscovery() }

        // Sayfa açıldığında otomatik tarama başlat
        checkAndStartDiscovery()

        // UI state toplama
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is DeviceUiState.Init -> {
                        binding.progressBar.isVisible = false
                        binding.btnStartDiscovery.isEnabled = true
                    }
                    is DeviceUiState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.btnStartDiscovery.isEnabled = false
                    }
                    is DeviceUiState.DevicesLoaded -> {
                        binding.progressBar.isVisible = false
                        binding.btnStartDiscovery.isEnabled = true
                        adapter.updateDeviceList(state.devices)
                        if (state.devices.isEmpty()) {
                            Toast.makeText(requireContext(),
                               "Hiç Bluetooth cihazı bulunamadı. Lütfen Bluetooth'un açık olduğundan emin olun.",
                                Toast.LENGTH_LONG).show()
                        }
                        findNavController().previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("device_selected", true)
                    }
                    is DeviceUiState.SensorDataLoaded -> {
                        binding.progressBar.isVisible = false
                        binding.btnStartDiscovery.isEnabled = true
                        //Toast.makeText(requireContext(),
                          //  "Veri alındı: ${state.data}",
                            //Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is DeviceUiState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.btnStartDiscovery.isEnabled = true
                        //Toast.makeText(requireContext(),
                            //state.message,
                            //Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun checkAndStartDiscovery() {
        val permissionsNeeded = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.BLUETOOTH_SCAN)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            requestPerm.launch(permissionsNeeded.toTypedArray())
        } else {
            startDiscovery()
        }

    }

    private fun startDiscovery() {
        viewModel.discoverDevices()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}