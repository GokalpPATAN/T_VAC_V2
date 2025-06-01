package com.erayerarslan.t_vac_kotlin.repository

import android.util.Log
import com.erayerarslan.t_vac_kotlin.model.Plant
import com.erayerarslan.t_vac_kotlin.model.SensorData
import com.erayerarslan.t_vac_kotlin.model.TreeRaw
import com.erayerarslan.t_vac_kotlin.model.toPlant
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiRepository @Inject constructor(
    private val model: GenerativeModel
) {

    suspend fun getSuggestions(sensor: SensorData): List<Plant> = withContext(Dispatchers.IO) {
        try {
            val prompt = """
                Sen akıllı bir tarım asistanısın.
                Aşağıdaki sensör değerlerine göre:
                - pH: ${sensor.phValue}
                - Sıcaklık: ${sensor.temperatureValue}°C
                - Nem: ${sensor.humidityValue}%
                - İletkenlik: ${sensor.conductibilityValue} mS/cm
                - Azot: ${sensor.azotValue}
                - Fosfor: ${sensor.fosforValue}
                - Potasyum: ${sensor.potasyumValue}
                - Konum: ${sensor.locationName}
                
                Konum bilgisine göre bu bölgedeki bitki örtüsü ile uyumlu ve Bu çevre koşullarına uygun **tam olarak 3 sebze,meyve, bitki türü veya ağaç türü** öner.
                Her bir sebze,meyve veya bitkinin için şu bilgileri JSON formatında ver:
                - name: Sebze,meyve veya bitkinin türü ismi
                - temperatureRange: [min sıcaklık, max sıcaklık]
                - humidityRange: [min nem, max nem]
                - features: Kısa özellik açıklaması
                - plantingInfo: Sebze,meyve veya bitkinin nasıl ekilmesi, sulanması, bakılması gerektiği
                - locationCompatibilityNote: Sebze,meyve veya bitkinin hangi bölgeye uyumlu olduğunu açıklayan bilgi
            
                Yanıtı sadece aşağıdaki gibi saf JSON dizisi olarak döndür (**başka hiçbir açıklama ekleme**):
            
                [
                  {
                    "name": "Zeytin Ağacı",
                    "temperatureRange": [20, 35],
                    "humidityRange": [30, 50],
                    "features": "Sıcak ve kuru iklimleri sever, az su ister.",
                    "plantingInfo": "Zeytin fidanları sonbaharda dikilmeli. Hafif alkali ve iyi drene toprak seçilmeli. İlk 2 yıl düzenli sulama yapılmalıdır.",
                    "locationCompatibilityNote": "Bu ağaç Ege Bölgesi'nde verimli sonuç verir."
                  },
                  ...
                ]
            """.trimIndent()

            val response = model.generateContent(prompt)

            val json = response.text?.replace("```json", "")?.replace("```", "")?.trim() ?: "[]"

            Log.d("GEMINI_RAW", "Yanıt: $json")

            // Önce TreeRaw listesine parse edeceğiz
            val treeRawType = object : TypeToken<List<TreeRaw>>() {}.type
            val rawList: List<TreeRaw> = Gson().fromJson(json, treeRawType)

            return@withContext rawList.map { it.toPlant() }


        } catch (e: Exception) {
            Log.e("GeminiRepository", "Error parsing Gemini response", e)
            emptyList()
        }
    }
}