package com.erayerarslan.t_vac_kotlin.model

data class HistoryItem(
    val data: SensorData,
    var isExpanded: Boolean = false
)
