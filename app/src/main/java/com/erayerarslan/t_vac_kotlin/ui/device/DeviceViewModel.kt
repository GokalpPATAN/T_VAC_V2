package com.farukayata.t_vac_kotlin.ui.device

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.farukayata.t_vac_kotlin.domain.usecase.DiscoverDevicesUseCase
import com.farukayata.t_vac_kotlin.domain.usecase.FetchDeviceSensorDataUseCase
import com.farukayata.t_vac_kotlin.domain.usecase.PairDeviceUseCase
import com.farukayata.t_vac_kotlin.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    application: Application,
    private val discoverDevicesUseCase: DiscoverDevicesUseCase,
    private val pairDeviceUseCase: PairDeviceUseCase,
    private val fetchDeviceSensorDataUseCase: FetchDeviceSensorDataUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Init)
    val uiState: StateFlow<DeviceUiState> = _uiState.asStateFlow()

    /** Bluetooth cihazlarını keşfet */
    fun discoverDevices() {
        viewModelScope.launch {
            _uiState.value = DeviceUiState.Loading
            try {
                val list = discoverDevicesUseCase()
                _uiState.value = DeviceUiState.DevicesLoaded(list)
            } catch (e: Exception) {
                _uiState.value = DeviceUiState.Error(e.localizedMessage ?: "Keşif hatası")
            }
        }
    }

    /** Seçilen cihazı eşleştir ve sensör verisini al */
    fun pairAndFetch(device: Device) {
        viewModelScope.launch {
            _uiState.value = DeviceUiState.Loading
            try {
                val ok = pairDeviceUseCase(device)
                if (!ok) {
                    _uiState.value = DeviceUiState.Error("Eşleştirme başarısız")
                    return@launch
                }
                val data = fetchDeviceSensorDataUseCase(device)
                _uiState.value = DeviceUiState.SensorDataLoaded(data)
            } catch (e: Exception) {
                _uiState.value = DeviceUiState.Error(e.localizedMessage ?: "Veri alma hatası")
            }
        }
    }
}
