package com.farukayata.t_vac_kotlin.repository

import android.util.Log
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.model.TreeRaw
import com.farukayata.t_vac_kotlin.model.toTree
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiRepository @Inject constructor(
    private val model: GenerativeModel
) {

    suspend fun getSuggestions(sensor: SensorData): List<Tree> = withContext(Dispatchers.IO) {
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
                
                Bu çevre koşullarına uygun **tam olarak 3 ağaç türü** öner.
                Yanıtı yalnızca aşağıdaki gibi ham JSON dizisi olarak döndür:
                
                [
                  {
                    "name": "Zeytin Ağacı",
                    "temperatureRange": [20, 35],
                    "humidityRange": [30, 50],
                    "features": "Sıcak ve kuru iklimleri sever, az su ister."
                  },
                  {
                    "name": "Çam Ağacı",
                    "temperatureRange": [-5, 30],
                    "humidityRange": [30, 60],
                    "features": "Soğuk iklimlere dayanıklıdır, hızlı büyür."
                  },
                  {
                    "name": "Kavak Ağacı",
                    "temperatureRange": [10, 25],
                    "humidityRange": [50, 70],
                    "features": "Su kaynaklarına yakın bölgelerde daha hızlı büyür."
                  }
                ]
                """.trimIndent()

            val response = model.generateContent(prompt)

            val json = response.text
                ?.replace("```json", "")
                ?.replace("```", "")
                ?.trim()
                ?: "[]"

            Log.d("GEMINI_RAW", "Yanıt: $json")

            // Önce TreeRaw listesine parse edeceğiz
            val treeRawType = object : TypeToken<List<TreeRaw>>() {}.type
            val rawList: List<TreeRaw> = Gson().fromJson(json, treeRawType)

            return@withContext rawList.map { it.toTree() }

            /*
            // TreeRaw → Tree dönüştürüyoruz
            return@withContext rawList.map { raw ->
                Tree(
                    name = raw.name,
                    temperatureRange = raw.temperatureRange[0]..raw.temperatureRange[1],
                    humidityRange = raw.humidityRange[0]..raw.humidityRange[1],
                    features = raw.features
                )
            }

             */


        } catch (e: Exception) {
            Log.e("GeminiRepository", "Error parsing Gemini response", e)
            emptyList()
        }
    }
}




/*
package com.farukayata.t_vac_kotlin.repository

import android.util.Log
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.Tree
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiRepository @Inject constructor(
    private val model: GenerativeModel
) {

    suspend fun getSuggestions(sensor: SensorData): List<Tree> = withContext(Dispatchers.IO) {
        val prompt = """
    You are a smart agriculture assistant.
    Based on the following sensor values:
    - pH: ${sensor.phValue}
    - Temperature: ${sensor.temperatureValue}°C
    - Humidity: ${sensor.humidityValue}%
    - Conductivity: ${sensor.conductibilityValue} mS/cm
    - Nitrogen: ${sensor.azotValue}
    - Phosphorus: ${sensor.fosforValue}
    - Potassium: ${sensor.potasyumValue}

    Suggest **exactly** 3 tree types suitable for this environment.
    ⚠️ **Return the result strictly as a raw JSON array** like this (with no extra commentary):
    [
      {
        "name": "Zeytin",
        "temperatureRange": [20, 35],
        "humidityRange": [30, 50],
        "features": "Sıcak ve kuru iklimleri sever, az su ister."
      },
      {
        "name": "Çam",
        "temperatureRange": [-5, 30],
        "humidityRange": [30, 60],
        "features": "Soğuk iklimlere de dayanıklıdır, hızlı büyür."
      },
      {
        "name": "Kavak",
        "temperatureRange": [10, 25],
        "humidityRange": [50, 70],
        "features": "Su kaynaklarına yakın bölgelerde daha hızlı büyür."
      }
    ]
""".trimIndent()
        /*
        val prompt = """
            You are a smart agriculture assistant.
            Based on the following sensor values:
            - pH: ${sensor.phValue}
            - Temperature: ${sensor.temperatureValue}°C
            - Humidity: ${sensor.humidityValue}%
            - Conductivity: ${sensor.conductibilityValue} mS/cm
            - Nitrogen: ${sensor.azotValue}
            - Phosphorus: ${sensor.fosforValue}
            - Potassium: ${sensor.potasyumValue}

            Suggest up to 6 tree or plant types suitable for growing in this environment.
            Return result strictly as JSON array. Example format:
            [
              {
                "name": "Zeytin",
                "temperatureRange": [20, 35],
                "humidityRange": [30, 50],
                "features": "Sıcak ve kuru iklimleri sever, az su ister."
              }
            ]
        """.trimIndent()
        */

        val response = model.generateContent(prompt)
        //val json = response.text ?: "[]"

        val json = response.text
            ?.replace("```json", "")
            ?.replace("```", "")
            ?.trim()
            ?: "[]"

        Log.d("GEMINI_RAW", "Yanıt: $json")

        // Parse edilen cevap Tree modeline uyarlanır
        val treeType = object : TypeToken<List<Tree>>() {}.type
        return@withContext Gson().fromJson(json, treeType)
    }
}
 */