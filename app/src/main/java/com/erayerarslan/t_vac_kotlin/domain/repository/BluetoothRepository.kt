package com.erayerarslan.t_vac_kotlin.domain.repository

import com.erayerarslan.t_vac_kotlin.model.Device
import com.erayerarslan.t_vac_kotlin.model.SensorData

interface BluetoothRepository {
    suspend fun discoverDevices(): List<Device>
    suspend fun pairDevice(device: Device): Boolean
    suspend fun fetchSensorData(device: Device): SensorData
}