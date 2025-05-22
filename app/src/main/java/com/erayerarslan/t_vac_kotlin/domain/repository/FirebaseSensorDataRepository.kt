package com.erayerarslan.t_vac_kotlin.domain.repository

import com.farukayata.t_vac_kotlin.domain.repository.SensorDataRepository
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSensorDataRepository @Inject constructor(
    private val manager: SensorDataManager
) : SensorDataRepository {

    override suspend fun fetchSensorData(): SensorData =
        manager.fetchSensorData().also { manager.addSensorData(it) } // 🔄

    override fun getLatestSensorData(): SensorData? =
        manager.getLatestSensorData()
}






/*
package com.erayerarslan.t_vac_kotlin.domain.repository

import com.farukayata.t_vac_kotlin.domain.repository.SensorDataRepository
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSensorDataRepository @Inject constructor(
    private val manager: SensorDataManager
) : SensorDataRepository {
    override suspend fun fetchSensorData(): SensorData =
        manager.fetchSensorData().also { manager.sensorData = it }

    override fun getLatestSensorData(): SensorData? =
        manager.sensorData
}

 */