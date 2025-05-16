package com.farukayata.t_vac_kotlin.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farukayata.t_vac_kotlin.R
import com.farukayata.t_vac_kotlin.databinding.FragmentHomeBinding
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import com.farukayata.t_vac_kotlin.ui.adapter.TreeAdapter
import com.farukayata.t_vac_kotlin.ui.device.DeviceViewModel
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val deviceViewModel by activityViewModels<DeviceViewModel>()

    private lateinit var adapter: TreeAdapter
    private lateinit var mapView: MapView

    private lateinit var soilMoistureChart: HorizontalBarChart
    private lateinit var phValueChart: HorizontalBarChart
    private lateinit var conductivityChart: HorizontalBarChart
    private lateinit var phosphorusChart: HorizontalBarChart
    private lateinit var potassiumChart: HorizontalBarChart
    private lateinit var nitrogenChart: HorizontalBarChart
    private lateinit var temperatureChart: HorizontalBarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TreeAdapter(emptyList()) { tree ->
            val action = HomeFragmentDirections.actionHomeFragmentToTreeDetailFragment(tree)
            findNavController().navigate(action)
        }

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState?.getBundle("MapViewBundleKey"))

        setupUI()
        setupCharts()
        setupTabs()
        observeViewModel()
        setupRefreshActions()
        setupLocationUpdates()
    }

    private fun setupUI() {
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.parseColor("#5B68DF"), Color.parseColor("#FFA555"))
        )
        binding.headerLayout.background = gradient
    }

    private fun setupCharts() {
        listOf(
            binding.soilMoistureChart,
            binding.phValueChart,
            binding.conductivityChart,
            binding.phosphorusChart,
            binding.potassiumChart,
            binding.nitrogenChart,
            binding.temperatureChart
        ).forEach { chart ->
            chart.apply {
                setDrawGridBackground(false)
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(false)
                setScaleEnabled(false)
                setPinchZoom(false)
                axisLeft.isEnabled = false
                axisRight.isEnabled = false
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    setDrawLabels(false)
                }
                minimumHeight = resources.getDimensionPixelSize(R.dimen.chart_height)
                setViewPortOffsets(0f, 0f, 0f, 0f)
            }
        }
        soilMoistureChart = binding.soilMoistureChart
        phValueChart = binding.phValueChart
        conductivityChart = binding.conductivityChart
        phosphorusChart = binding.phosphorusChart
        potassiumChart = binding.potassiumChart
        nitrogenChart = binding.nitrogenChart
        temperatureChart = binding.temperatureChart
    }

    private fun setupTabs() {
        val scrollView = binding.root.findViewById<ScrollView>(R.id.scrollView)
        val mapView = binding.mapView
        val parameterHeaderRow = binding.root.findViewById<LinearLayout>(R.id.parameterHeaderRow)
        val tabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.text) {
                    "Maps" -> {
                        scrollView.visibility = View.GONE
                        mapView.visibility = View.VISIBLE
                        parameterHeaderRow.visibility = View.GONE
                    }
                    else -> {
                        scrollView.visibility = View.VISIBLE
                        mapView.visibility = View.GONE
                        parameterHeaderRow.visibility = View.VISIBLE
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun observeViewModel() {
        viewModel.sensorData.observe(viewLifecycleOwner) { data ->
            updateChartsAndCounts(data)
            data.temperatureValue?.toInt()?.let { t ->
                data.humidityValue?.toInt()?.let { h ->
                    viewModel.fetchTreeList(t, h)
                }
            }
        }

    }

    private fun setupRefreshActions() {

        binding.refreshDataButton.setOnClickListener {
            deviceViewModel.requestNewSensorData(
                selectedDevice = deviceViewModel.devices.value?.find { it.isPaired },
                onDataReceived = {
                    viewModel.refreshSensorData()
                    showAnalysisDate()
                },
                onDeviceMissing = {
                    Toast.makeText(requireContext(), "Bağlı cihaz bulunamadı!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun setupLocationUpdates() {
        mapView.getMapAsync { googleMap ->
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.isMyLocationEnabled = true
                val client = LocationServices.getFusedLocationProviderClient(requireActivity())
                val req = LocationRequest.create().apply {
                    interval = 5 * 60 * 1000L
                    fastestInterval = 2 * 60 * 1000L
                    priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                }
                val callback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        result.lastLocation?.let { loc ->
                            val geo = Geocoder(requireContext(), Locale.getDefault())
                            val city = geo.getFromLocation(loc.latitude, loc.longitude, 1)?.firstOrNull()?.adminArea ?: "Bilinmiyor"
                            viewModel.onLocationUpdated(city)
                            SensorDataManager.sensorData?.locationName = city
                        }
                    }
                }
                client.requestLocationUpdates(req, callback, Looper.getMainLooper())
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            }
        }
    }

    private fun updateChartsAndCounts(data: SensorData) {
        var normal = 0
        var critical = 0
        fun update(chart: HorizontalBarChart, name: String, value: Float?, statusFn: (Float)->String, colorFn: (Float)->String) {
            value?.let { v ->
                val status = statusFn(v)
                setupParameterChart(chart, name, colorFn(v), status)
                if (status == "Normal") normal++ else critical++
            }
        }
        update(soilMoistureChart, "Soil Moisture", data.humidityValue, viewModel::getHumidityStatus, viewModel::getHumidityColor)
        update(phValueChart, "pH Value", data.phValue, viewModel::getPhStatus, viewModel::getPhColor)
        update(conductivityChart, "Conductivity", data.conductibilityValue, viewModel::getConductivityStatus, viewModel::getConductivityColor)
        update(phosphorusChart, "Phosphorus", data.fosforValue, viewModel::getPhosphorusStatus, viewModel::getPhosphorusColor)
        update(potassiumChart, "Potassium", data.potasyumValue, viewModel::getPotassiumStatus, viewModel::getPotassiumColor)
        update(nitrogenChart, "Nitrogen", data.azotValue, viewModel::getNitrogenStatus, viewModel::getNitrogenColor)
        update(temperatureChart, "Temperature", data.temperatureValue, viewModel::getTemperatureStatus, viewModel::getTemperatureColor)
        binding.optimalTextView.text = normal.toString()
        binding.criticalTextView.text = critical.toString()
        binding.totalTextView.text = (normal + critical).toString()
        Log.d("HomeResponse","$data")
    }

    private fun setupParameterChart(chart: HorizontalBarChart, parameter: String, colorHex: String, status: String) {
        val barValue = when (status) {
            "Very Low"  -> 0.10f
            "Low"      -> 0.25f
            "Normal"   -> 0.50f
            "High"     -> 0.75f
            "Very High"-> 1.0f
            else        -> 0f
        }
        val entries = arrayListOf(com.github.mikephil.charting.data.BarEntry(0f, barValue))
        val dataSet = com.github.mikephil.charting.data.BarDataSet(entries, parameter).apply {
            color = Color.parseColor(colorHex)
            setDrawValues(false)
        }
        val data = com.github.mikephil.charting.data.BarData(dataSet).apply { barWidth = 0.5f }
        chart.data = data
        val idx = when (parameter) {
            "Soil Moisture" -> 0
            "pH Value"      -> 1
            "Conductivity"  -> 2
            "Phosphorus"    -> 3
            "Potassium"     -> 4
            "Nitrogen"      -> 5
            "Temperature"   -> 6
            else             -> -1
        }
        if (idx >= 0) {
            binding.root.findViewById<TextView>(
                resources.getIdentifier("parameter_name_$idx", "id", requireContext().packageName)
            )?.apply { text = parameter }
            binding.root.findViewById<TextView>(
                resources.getIdentifier("parameter_status_$idx", "id", requireContext().packageName)
            )?.apply { text = status; setTextColor(Color.parseColor(colorHex)) }
        }
        chart.invalidate()
    }

    private fun showAnalysisDate() {
        val now = System.currentTimeMillis()
        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        binding.lastUpdatedText.text = "Tıklama Tarihi: ${fmt.format(Date(now))}"
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}