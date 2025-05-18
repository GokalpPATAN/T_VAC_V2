package com.farukayata.t_vac_kotlin.domain.usecase

import com.farukayata.t_vac_kotlin.domain.repository.BluetoothRepository
import com.farukayata.t_vac_kotlin.model.Device
import com.farukayata.t_vac_kotlin.model.SensorData
import javax.inject.Inject

/**
 * Eşleşmiş cihazdan sensör verisini okur.
 */
class FetchDeviceSensorDataUseCase @Inject constructor(
    private val repository: BluetoothRepository
) {
    suspend operator fun invoke(device: Device): SensorData =
        repository.fetchSensorData(device)
}