package com.farukayata.t_vac_kotlin.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukayata.t_vac_kotlin.domain.usecase.FetchSensorDataUseCase
import com.farukayata.t_vac_kotlin.domain.usecase.GetLatestSensorDataUseCase
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import com.farukayata.t_vac_kotlin.model.SensorDataManager.fetchSensorData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchSensorDataUseCase: FetchSensorDataUseCase,
    private val getLatestSensorDataUseCase: GetLatestSensorDataUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _uiState     = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow(Tab.PARAMETERS)
    val selectedTab: StateFlow<Tab> = _selectedTab.asStateFlow()

    init { refreshSensorData() }

    fun refreshSensorData() = viewModelScope.launch {
        flow { emit(fetchSensorData()) }
            .onStart { _uiState.value = HomeUiState.Loading }
            .catch   { _uiState.value = HomeUiState.Error(it.localizedMessage ?: "Beklenmeyen hata") }
            .collect { _uiState.value = HomeUiState.Success(it, buildCharts(it)) }
    }

    fun onTabSelected(tab: Tab) { _selectedTab.value = tab }

    /** Konum adını (şehir) en güncel ölçüme ekler. */
    fun updateLocation(city: String) = viewModelScope.launch {
        val latest = getLatestSensorDataUseCase() ?: return@launch            // 🔄
        val updated = latest.copy(locationName = city)
        SensorDataManager.addSensorData(updated)                              // 🔄
        val charts = buildCharts(updated)
        _uiState.value = HomeUiState.Success(updated, charts)
    }

    /* buildCharts() ve yardımcı fonksiyonların TAMAMI değişmedi */
    private fun buildCharts(data: SensorData): List<ParameterChart> =
        listOf(
            makeChart("Soil Moisture", data.humidityValue, ::getHumidityStatus, ::getHumidityColor),
            makeChart("pH Value", data.phValue, ::getPhStatus, ::getPhColor),
            makeChart(
                "Conductivity",
                data.conductibilityValue,
                ::getConductivityStatus,
                ::getConductivityColor
            ),
            makeChart("Phosphorus", data.fosforValue, ::getPhosphorusStatus, ::getPhosphorusColor),
            makeChart("Potassium", data.potasyumValue, ::getPotassiumStatus, ::getPotassiumColor),
            makeChart("Nitrogen", data.azotValue, ::getNitrogenStatus, ::getNitrogenColor),
            makeChart(
                "Temperature",
                data.temperatureValue,
                ::getTemperatureStatus,
                ::getTemperatureColor
            )
        )

    private fun makeChart(
        name: String,
        value: Float?,
        statusFn: (Float) -> String,
        colorFn: (Float) -> String
    ): ParameterChart {
        if (value == null) return ParameterChart(name, 0f, "#CCCCCC", "No Data")
        val status = statusFn(value)
        val barValue = when (status) {
            "Very Low" -> .10f; "Low" -> .25f; "Normal" -> .50f
            "High" -> .75f; "Very High" -> 1f
            else -> 0f
        }
        return ParameterChart(name, barValue, colorFn(value), status)
    }


    fun getPhStatus(value: Float): String = when {
        value < 5.0f -> "Very Low"
        value < 5.5f -> "Low"
        value < 6.5f -> "Normal"
        value < 7.5f -> "High"
        else -> "Very High"
    }

    fun getTemperatureStatus(value: Float): String = when {
        value < 5f -> "Very Low"
        value < 10f -> "Low"
        value < 25f -> "Normal"
        value < 30f -> "High"
        else -> "Very High"
    }

    fun getConductivityStatus(value: Float): String = when {
        value < 0.2f -> "Very Low"
        value < 0.5f -> "Low"
        value < 1.5f -> "Normal"
        value < 2.5f -> "High"
        else -> "Very High"
    }

    fun getPhosphorusStatus(value: Float): String = when {
        value < 5f -> "Very Low"
        value < 10f -> "Low"
        value < 30f -> "Normal"
        value < 50f -> "High"
        else -> "Very High"
    }

    fun getPotassiumStatus(value: Float): String = when {
        value < 50f -> "Very Low"
        value < 100f -> "Low"
        value < 200f -> "Normal"
        value < 300f -> "High"
        else -> "Very High"
    }

    fun getNitrogenStatus(value: Float): String = when {
        value < 0.1f -> "Very Low"
        value < 0.5f -> "Low"
        value < 1.0f -> "Normal"
        value < 2.0f -> "High"
        else -> "Very High"
    }

    fun getHumidityStatus(value: Float): String = when {
        value < 10f -> "Very Low"
        value < 25f -> "Low"
        value < 40f -> "Normal"
        value < 60f -> "High"
        else -> "Very High"
    }

    fun getPhColor(value: Float): String = when {
        value < 5.0f -> "#FF0000"
        value < 5.5f -> "#FFA500"
        value < 6.5f -> "#00FF00"
        value < 7.5f -> "#FFA500"
        else -> "#FF0000"
    }

    fun getTemperatureColor(value: Float): String = when {
        value < 5f -> "#FF0000"
        value < 10f -> "#FFA500"
        value < 25f -> "#00FF00"
        value < 30f -> "#FFA500"
        else -> "#FF0000"
    }

    fun getConductivityColor(value: Float): String = when {
        value < 0.2f -> "#FF0000"
        value < 0.5f -> "#FFA500"
        value < 1.5f -> "#00FF00"
        value < 2.5f -> "#FFA500"
        else -> "#FF0000"
    }

    fun getPhosphorusColor(value: Float): String = when {
        value < 5f -> "#FF0000"
        value < 10f -> "#FFA500"
        value < 30f -> "#00FF00"
        value < 50f -> "#FFA500"
        else -> "#FF0000"
    }

    fun getPotassiumColor(value: Float): String = when {
        value < 50f -> "#FF0000"
        value < 100f -> "#FFA500"
        value < 200f -> "#00FF00"
        value < 300f -> "#FFA500"
        else -> "#FF0000"
    }

    fun getNitrogenColor(value: Float): String = when {
        value < 0.1f -> "#FF0000"
        value < 0.5f -> "#FFA500"
        value < 1.0f -> "#00FF00"
        value < 2.0f -> "#FFA500"
        else -> "#FF0000"
    }

    fun getHumidityColor(value: Float): String = when {
        value < 10f -> "#FF0000"
        value < 25f -> "#FFA500"
        value < 40f -> "#00FF00"
        value < 60f -> "#FFA500"
        else -> "#FF0000"
    }
}
