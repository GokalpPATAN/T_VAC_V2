package com.farukayata.t_vac_kotlin.model

import kotlin.random.Random

data class SensorData(
    val phValue:  Float? = 1f,
    val temperatureValue: Float?  = 1f,
    val conductibilityValue: Float?  = 1f ,
    val fosforValue: Float?  =  1f ,
    val humidityValue: Float?  =  1f ,
    val potasyumValue: Float?  =  1f ,
    val azotValue: Float?  =  1f,
    var locationName: String? = "Bilinmiyor"
)

object SensorDataManager {
    var sensorData: SensorData? = null

    fun fetchSensorData(): SensorData {
        val ph     = sensorData?.phValue             ?: generateRandomFloat(5.0f, 7.5f)
        val temp   = sensorData?.temperatureValue    ?: generateRandomFloat(5.0f, 30.0f)
        val cond   = sensorData?.conductibilityValue ?: generateRandomFloat(0.2f, 2.5f)
        val fosfor = sensorData?.fosforValue         ?: generateRandomFloat(5.0f, 50.0f)
        val humid  = sensorData?.humidityValue       ?: generateRandomFloat(10.0f, 60.0f)
        val potas  = sensorData?.potasyumValue       ?: generateRandomFloat(50.0f, 300.0f)
        val azot   = sensorData?.azotValue           ?: generateRandomFloat(0.1f, 2.0f)

        return SensorData(
            phValue             = ph,
            temperatureValue    = temp,
            conductibilityValue = cond,
            fosforValue         = fosfor,
            humidityValue       = humid,
            potasyumValue       = potas,
            azotValue           = azot,
        )
    }

    private fun generateRandomFloat(min: Float, max: Float): Float =
        Random.nextFloat().let { it * (max - min) + min
        }
}



