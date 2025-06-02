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

    fun InitialSensor(count: Int = 1) {
        repeat(count) {
            val sensorData = SensorDataGenerator.SensorData()
            addSensorData(sensorData)
        }
        Log.d("SensorDataManager", "Sensör verileri oluşturuldu: $count adet")
    }

    fun getAll(): List<SensorData> = sensorDataList.toList()
}

object SensorDataGenerator {
    private val locations = listOf("Alibeyköy")

    fun SensorData(): SensorData {
        return SensorData(
            phValue = 6.50f + (-0.1f..0.1f).randomStep(0.01f),              // 6.4 – 6.6 (hafif alkali sapma)
            temperatureValue = 25.0f + (0.5f..1.5f).randomStep(0.1f),       // 25.5 – 26.5°C (el sıcaklığı etkisi)
            conductibilityValue = 2.00f + (0.1f..0.3f).randomStep(0.01f),   // 2.1 – 2.3 (ter ve iyonlar)
            fosforValue = 30.0f + (-0.2f..0.2f).randomStep(0.05f),          // ~nötr etki
            humidityValue = 40.0f + (5f..10f).randomStep(0.5f),             // 45 – 50 (el nemi etkisi)
            potasyumValue = 150.0f + (-1f..1f).randomStep(0.2f),            // etkisiz (doğal varyasyon)
            azotValue = 1.000f + (-0.01f..0.01f).randomStep(0.001f),        // çok hafif varyasyon
            locationName = locations.random(),
            lastUpdateTime = System.currentTimeMillis()
        )
    }


    fun ClosedFloatingPointRange<Float>.randomStep(step: Float): Float {
        val steps = ((endInclusive - start) / step).toInt()
        return (0..steps).random() * step + start
    }


}



