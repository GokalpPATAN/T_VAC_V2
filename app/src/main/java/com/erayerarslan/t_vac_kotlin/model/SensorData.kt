package com.erayerarslan.t_vac_kotlin.model

import android.util.Log

/** Sensörden gelen tek seferlik ölçüm. */
data class SensorData(
    val phValue: Float? = 1f,
    val temperatureValue: Float? = 1f,
    val conductibilityValue: Float? = 1f,
    val fosforValue: Float? = 1f,
    val humidityValue: Float? = 1f,
    val potasyumValue: Float? = 1f,
    val azotValue: Float? = 1f,
    var locationName: String? = "Bilinmiyor",
    val lastUpdateTime: Long = System.currentTimeMillis()
)

/** Ölçüm geçmişini yöneten singleton. */
object SensorDataManager {

    private val sensorDataList = mutableListOf<SensorData>()

    /** Eski setter geri uyumluluk için; yeni veri eklemede addSensorData kullanılır. */
    @Deprecated("Kullanımda: addSensorData()", ReplaceWith("addSensorData(value)"))
    var sensorData: SensorData?
        get() = sensorDataList.lastOrNull()
        set(value) { value?.let { addSensorData(it) } }

    /** Yalnızca ölçümler farklıysa listeye ekler; aynı ölçüm ise meta/zamanı günceller. */
    fun addSensorData(newData: SensorData) {
        val last = sensorDataList.lastOrNull()
        val sameMeasurement = last?.let {
            it.phValue == newData.phValue &&
                    it.temperatureValue == newData.temperatureValue &&
                    it.conductibilityValue == newData.conductibilityValue &&
                    it.fosforValue == newData.fosforValue &&
                    it.humidityValue == newData.humidityValue &&
                    it.potasyumValue == newData.potasyumValue &&
                    it.azotValue == newData.azotValue
        } ?: false

        if (sameMeasurement) {
            sensorDataList[sensorDataList.lastIndex] =
                last!!.copy(
                    locationName = newData.locationName ?: last.locationName,
                    lastUpdateTime = System.currentTimeMillis()
                )
        } else {
            sensorDataList.add(newData)
        }
        Log.d("SensorDataManager", "Listedeki son veri: ${sensorDataList.last()}")
    }

    /** En güncel ölçüm (null dönebilir). */
    fun getLatestSensorData(): SensorData? = sensorDataList.lastOrNull()

    /** Liste boşsa hata fırlatır. */
    fun fetchSensorData(): SensorData =
        sensorDataList.lastOrNull()
            ?: throw IllegalStateException("Sensör verisi bulunamadı")

    fun clearSensorData() {
        sensorDataList.clear()
        Log.d("SensorDataManager", "Sensör verileri temizlendi")
    }
}

