package com.erayerarslan.t_vac_kotlin.domain.repository

import com.erayerarslan.t_vac_kotlin.model.SensorData

interface SensorDataRepository {
    suspend fun fetchSensorData(): SensorData
    fun getLatestSensorData(): SensorData?
}