package com.farukayata.t_vac_kotlin.domain.usecase

import com.farukayata.t_vac_kotlin.domain.repository.SensorDataRepository
import com.farukayata.t_vac_kotlin.model.SensorData
import javax.inject.Inject

/**
 * Sensör verisini repository üzerinden getirir.
 */
class FetchSensorDataUseCase @Inject constructor(
    private val repository: SensorDataRepository
) {
    suspend operator fun invoke(): SensorData =
        repository.fetchSensorData()
}