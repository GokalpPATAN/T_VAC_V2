package com.erayerarslan.t_vac_kotlin.ui.adapter

import com.erayerarslan.t_vac_kotlin.model.Plant
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.databinding.ItemTreeBinding

class PlantAdapter(
    private var plantList: List<Plant>,
    private val onItemClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(private val binding: ItemTreeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: Plant) {
            // Görsel yükleme (URL bazlı)
            if (plant.img.isNotBlank()) {
                Glide.with(binding.root.context)
                    .load(plant.img)
                    .placeholder(R.drawable.ic_default_tree)
                    .error(R.drawable.ic_default_tree)
                    .into(binding.treeImg)
            } else {
                binding.treeImg.setImageResource(R.drawable.ic_default_tree)
            }

            // Metin atamaları
            binding.treeName.text = "Bitki Adı: ${plant.name}"
            binding.treeTemp.text =
                "Sıcaklık: ${plant.temperatureRange.first()}–${plant.temperatureRange.last()} °C"
            binding.treeHumadity.text =
                "Nem: ${plant.humidityRange.first()}–${plant.humidityRange.last()} %"
            binding.treeFeatures.text = plant.features

            binding.root.setOnClickListener { onItemClick(plant) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding =
            ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(plantList[position])
    }

    override fun getItemCount(): Int {
        Log.d("PlantAdapter", "Item count: ${plantList.size}")
        return plantList.size
    }

    /**
     * Listeyi güncelle ve yalnızca değişen satırları yeniden çiz
     */
    fun updatePlantList(newPlantList: List<Plant>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = plantList.size
            override fun getNewListSize() = newPlantList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                // Eğer her bir öğeyi benzersiz isme göre ayırt edebiliyorsanız:
                return plantList[oldItemPosition].name == newPlantList[newItemPosition].name
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return plantList[oldItemPosition] == newPlantList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        plantList = newPlantList
        diffResult.dispatchUpdatesTo(this)
    }
}
