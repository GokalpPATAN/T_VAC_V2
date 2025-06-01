package com.erayerarslan.t_vac_kotlin.ui.home

/**
 * Data class representing a single parameter's chart values
 */
data class ParameterChart(
    val name: String,
    val barValue: Float,
    val colorHex: String,
    val status: String
)