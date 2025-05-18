package com.farukayata.t_vac_kotlin.ui.home

import com.farukayata.t_vac_kotlin.model.SensorData

sealed class HomeUiState {
    object Init    : HomeUiState()
    object Loading : HomeUiState()
    data class Success(
        val sensorData: SensorData,
        val charts: List<ParameterChart>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
