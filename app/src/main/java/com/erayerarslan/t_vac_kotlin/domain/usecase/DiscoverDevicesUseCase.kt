package com.farukayata.t_vac_kotlin.domain.usecase

import com.farukayata.t_vac_kotlin.domain.repository.BluetoothRepository
import com.farukayata.t_vac_kotlin.model.Device
import javax.inject.Inject

/**
 * Bluetooth cihazları keşfeder.
 */
class DiscoverDevicesUseCase @Inject constructor(
    private val repository: BluetoothRepository
) {
    suspend operator fun invoke(): List<Device> =
        repository.discoverDevices()
}