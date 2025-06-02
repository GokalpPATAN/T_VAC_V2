package com.erayerarslan.t_vac_kotlin.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.model.SensorData

fun ImageView.loadImage(url: String?, placeholder: Int = R.drawable.ic_acacia, error: Int = R.drawable.ic_add) {
    Glide.with(this.context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadImage(resourceId: Int, placeholder: Int = R.drawable.ic_willow) {
    Glide.with(this.context)
        .load(resourceId)
        .apply(
            RequestOptions()
                .placeholder(placeholder)
                .centerCrop()
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

object SensorDataMenager {
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