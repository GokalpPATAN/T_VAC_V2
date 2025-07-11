package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.domain.repository.BluetoothRepository
import com.erayerarslan.t_vac_kotlin.model.Device
import javax.inject.Inject

/**
 * Seçilen cihazı eşleştirir.
 */
class PairDeviceUseCase @Inject constructor(
    private val repository: BluetoothRepository
) {
    suspend operator fun invoke(device: Device): Boolean =
        repository.pairDevice(device)
}