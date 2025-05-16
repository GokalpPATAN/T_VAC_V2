package com.farukayata.t_vac_kotlin.model

import com.farukayata.t_vac_kotlin.model.Plant

/**
 * Gemini API'den gelen ham JSON veri modeli.
 */
data class TreeRaw(
    val name: String,
    val temperatureRange: List<Int>,
    val humidityRange: List<Int>,
    val features: String,
    val plantingInfo: String,
    val locationCompatibilityNote: String? = null
)

/**
 * TreeRaw nesnesini uygulamada kullandığımız Plant modeline dönüştürür.
 */
fun TreeRaw.toPlant(): Plant = Plant(
    name = this.name,
    category = "",                            // Gerekirse kategori eklenebilir
    temperatureRange = this.temperatureRange,
    humidityRange = this.humidityRange,
    features = this.features,
    img = "",                                 // Varsayılan resim URL'i boş
    plantingInfo = this.plantingInfo,
    locationNote = this.locationCompatibilityNote ?: ""
)
