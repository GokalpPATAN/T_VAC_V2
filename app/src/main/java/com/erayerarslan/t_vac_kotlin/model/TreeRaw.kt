package com.farukayata.t_vac_kotlin.model


//gemini JSON cevab gelicek onu Gson ile TreeRaw listesine çeviriyoruz
data class TreeRaw(
    val name: String,
    val temperatureRange: List<Int>,
    val humidityRange: List<Int>,
    val features: String,
    val plantingInfo: String
)

fun TreeRaw.toTree(): Tree {
    return Tree(
        name = name,
        temperatureRange = temperatureRange[0]..temperatureRange[1],
        humidityRange = humidityRange[0]..humidityRange[1],
        features = features,
        plantingInfo = plantingInfo
    )
}
