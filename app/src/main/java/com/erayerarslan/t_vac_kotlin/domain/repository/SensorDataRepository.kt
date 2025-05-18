package com.farukayata.t_vac_kotlin.domain.repository

import com.farukayata.t_vac_kotlin.model.SensorData

interface SensorDataRepository {
    suspend fun fetchSensorData(): SensorData
    fun getLatestSensorData(): SensorData?
}