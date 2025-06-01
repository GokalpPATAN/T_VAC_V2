package com.erayerarslan.t_vac_kotlin.ui.adapter

import com.erayerarslan.t_vac_kotlin.model.Plant
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.databinding.ItemTreeBinding
import com.erayerarslan.t_vac_kotlin.api.OpenAIClient
import com.erayerarslan.t_vac_kotlin.api.ImageGenerationRequest
import com.erayerarslan.t_vac_kotlin.ui.search.SearchViewModel
import kotlinx.coroutines.*

class SearchAdapter(
    private var plantList: List<Plant>,
    private val viewModel: SearchViewModel,
    private val onItemClick: (Plant) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val imageGenerationJobs = mutableMapOf<String, Job>()
    private val imageUrlCache = mutableMapOf<String, String>()

    inner class SearchViewHolder(private val binding: ItemTreeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: Plant) {
            binding.progressBar.visibility = View.GONE

            // AI tarafından üretilmiş görsel yoksa üret, varsa yükle
            if (plant.img.isNullOrBlank()) {
                generatePlantImage(plant)
            } else {
                loadImageWithGlide(plant.img, R.drawable.ic_default_tree)
            }

            // Metinleri atama
            binding.treeName.text = plant.name
            binding.treeTemp.text = if (plant.temperatureRange.isNotEmpty())
                "Sıcaklık: ${plant.temperatureRange.first()} ile ${plant.temperatureRange.last()} °C"
            else
                "Sıcaklık verisi yok"
            binding.treeHumadity.text = if (plant.humidityRange.isNotEmpty())
                "Nem: ${plant.humidityRange.first()} ile ${plant.humidityRange.last()} %"
            else
                "Nem verisi yok"
            binding.treeFeatures.text = plant.features

            binding.root.setOnClickListener { onItemClick(plant) }
        }

        private fun loadImageWithGlide(url: String, fallback: Int) {
            binding.progressBar.visibility = View.VISIBLE
            Glide.with(binding.root.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(fallback)
                .error(fallback)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        Log.e("SearchAdapter", "Image load failed: ${e?.message}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.treeImg)
        }

        private fun generatePlantImage(plant: Plant) {
            val key = plant.name
            imageGenerationJobs[key]?.cancel()

            // Cache kontrolü
            imageUrlCache[key]?.let { cachedUrl ->
                loadImageWithGlide(cachedUrl, R.drawable.ic_default_tree)
                return
            }

            binding.progressBar.visibility = View.VISIBLE
            binding.treeImg.setImageResource(R.drawable.ic_default_tree)

            val job = coroutineScope.launch {
                val prompt = buildString {
                    append("A high-quality illustration of a full-grown ${plant.name}. ")
                    append("Botanical-style digital illustration on white background. ")
                    append("Show full structure: trunk, branches, leaves. No watermark. 4K resolution.")
                }
                try {
                    val response = withContext(Dispatchers.IO) {
                        OpenAIClient.service.generateImage(
                            ImageGenerationRequest(
                                prompt = prompt,
                                n = 1,
                                size = "512x512"
                            )
                        )
                    }
                    val url = response.body()?.data?.firstOrNull()?.url
                    withContext(Dispatchers.Main) {
                        if (!url.isNullOrBlank()) {
                            imageUrlCache[key] = url
                            loadImageWithGlide(url, R.drawable.ic_default_tree)
                            val pos = adapterPosition
                            if (pos != RecyclerView.NO_POSITION) {
                                val updated = plant.copy(img = url)
                                plantList = plantList.toMutableList().apply { set(pos, updated) }
                                notifyItemChanged(pos)
                                viewModel.updateSuggestedPlantImage(plant.name, url)
                            }
                        } else {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SearchAdapter", "Error generating image", e)
                    withContext(Dispatchers.Main) { binding.progressBar.visibility = View.GONE }
                }
            }
            imageGenerationJobs[key] = job
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(plantList[position])
    }

    override fun getItemCount(): Int = plantList.size

    fun updatePlantList(newList: List<Plant>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = plantList.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                plantList[oldPos].name == newList[newPos].name
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                plantList[oldPos] == newList[newPos]
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        plantList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        coroutineScope.cancel()
        imageGenerationJobs.clear()
    }
}
