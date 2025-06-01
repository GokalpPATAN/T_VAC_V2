package com.erayerarslan.t_vac_kotlin.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erayerarslan.t_vac_kotlin.ui.home.ParameterChart
import com.erayerarslan.t_vac_kotlin.R
import androidx.core.graphics.toColorInt

class ParameterAdapter(
    private var items: List<ParameterChart>
) : RecyclerView.Adapter<ParameterAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView         = view.findViewById(R.id.tvParamName)
        val status: TextView       = view.findViewById(R.id.tvStatus)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parameter, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val chart = items[position]
        holder.name.text = chart.name
        holder.status.text = chart.status
        // 0f–1f arası barValue’yu %’ye çevir
        holder.progressBar.progress = (chart.barValue * 100).toInt()
        // Renk dinamik olarak
        holder.progressBar.progressDrawable.setTint(chart.colorHex.toColorInt())
    }

    override fun getItemCount() = items.size

    fun submitList(new: List<ParameterChart>) {
        items = new
        notifyDataSetChanged()
    }
}
