package com.erayerarslan.t_vac_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.model.SensorData
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<SensorData, HistoryAdapter.HistoryViewHolder>(SensorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val sensorData = getItem(position)

        holder.timestampText.text = formatTimestamp(sensorData.lastUpdateTime)
        holder.detailLayout.visibility = View.GONE

        holder.itemView.setOnClickListener {
            holder.detailLayout.visibility =
                if (holder.detailLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        holder.ph.text = "pH: ${sensorData.phValue}"
        holder.temp.text = "Sıcaklık: ${sensorData.temperatureValue} °C"
        holder.humidity.text = "Nem: ${sensorData.humidityValue} %"
        holder.fosfor.text = "Fosfor: ${sensorData.fosforValue}"
        holder.potasyum.text = "Potasyum: ${sensorData.potasyumValue}"
        holder.azot.text = "Azot: ${sensorData.azotValue}"
        holder.iletkenlik.text = "İletkenlik: ${sensorData.conductibilityValue}"
        holder.konum.text = "Konum: ${sensorData.locationName}"
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timestampText: TextView = view.findViewById(R.id.tvTimestamp)
        val detailLayout: View = view.findViewById(R.id.detailLayout)

        val ph: TextView = view.findViewById(R.id.tvPh)
        val temp: TextView = view.findViewById(R.id.tvTemp)
        val humidity: TextView = view.findViewById(R.id.tvHumidity)
        val fosfor: TextView = view.findViewById(R.id.tvFosfor)
        val potasyum: TextView = view.findViewById(R.id.tvPotasyum)
        val azot: TextView = view.findViewById(R.id.tvAzot)
        val iletkenlik: TextView = view.findViewById(R.id.tvConductibility)
        val konum: TextView = view.findViewById(R.id.tvLocation)
    }

    class SensorDiffCallback : DiffUtil.ItemCallback<SensorData>() {
        override fun areItemsTheSame(oldItem: SensorData, newItem: SensorData): Boolean {
            return oldItem.lastUpdateTime == newItem.lastUpdateTime
        }

        override fun areContentsTheSame(oldItem: SensorData, newItem: SensorData): Boolean {
            return oldItem == newItem
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
