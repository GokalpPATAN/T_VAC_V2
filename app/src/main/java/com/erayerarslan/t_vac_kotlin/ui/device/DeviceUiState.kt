package com.farukayata.t_vac_kotlin.ui.device

import com.farukayata.t_vac_kotlin.model.Device
import com.farukayata.t_vac_kotlin.model.SensorData

sealed class DeviceUiState {
    object Init : DeviceUiState()

    object Loading : DeviceUiState()

    /** Cihaz listesi yüklendiğinde */
    data class DevicesLoaded(val devices: List<Device>) : DeviceUiState()

    /** Sensör verisi okunduğunda */
    data class SensorDataLoaded(val data: SensorData) : DeviceUiState()

    /** Bir hata meydana geldiğinde */
    data class Error(val message: String) : DeviceUiState()
}
