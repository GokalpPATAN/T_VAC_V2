package com.erayerarslan.t_vac_kotlin.domain.usecase

import com.erayerarslan.t_vac_kotlin.domain.repository.SensorDataRepository
import com.erayerarslan.t_vac_kotlin.model.SensorData
import javax.inject.Inject

/**
 * Repository'den son alınan sensör verisini döner.
 */
class GetLatestSensorDataUseCase @Inject constructor(
    private val repository: SensorDataRepository
) {
    operator fun invoke(): SensorData? =
        repository.getLatestSensorData()
}