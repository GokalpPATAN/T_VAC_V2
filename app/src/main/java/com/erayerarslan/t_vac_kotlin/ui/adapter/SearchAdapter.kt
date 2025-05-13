package com.farukayata.t_vac_kotlin.ui.adapter

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
import com.farukayata.t_vac_kotlin.R
import com.farukayata.t_vac_kotlin.databinding.ItemTreeBinding
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.util.loadImage
import com.farukayata.t_vac_kotlin.api.OpenAIClient
import com.farukayata.t_vac_kotlin.api.ImageGenerationRequest
import kotlinx.coroutines.*

class SearchAdapter(
    private var treeList2: List<Tree>,
    private val onItemClick: (Tree) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    //ViewHolder ile coroutin leri yönetcez
    private val imageGenerationJobs = mutableMapOf<String, Job>()
    private val imageUrlCache = mutableMapOf<String, String>() //üretilen ağaç image url sini tuttuk

    inner class SearchViewHolder(val binding: ItemTreeBinding) : RecyclerView.ViewHolder(binding.root) {
        val treeName = binding.treeName
        val treeTemp = binding.treeTemp
        val treeHumadity = binding.treeHumadity
        val treeFeatures = binding.treeFeatures

        fun bind(tree: Tree) {
            binding.progressBar.visibility = View.GONE

            if (isAIGeneratedTree(tree) && tree.imageUrl == null) {
                //ağaç görseli lokal listemizde yoka ai tarafından üretim başlatcaz
                generateTreeImage(tree)
            } else {
                if (tree.imageUrl != null) {
                    loadImageWithGlide(tree.imageUrl, tree.img)//url varsa glideile yükledik
                } else {
                    binding.treeImg.loadImage(tree.img)
                }
            }

            treeName.text = tree.name
            treeTemp.text = "Sıcaklık: ${tree.temperatureRange.start} ile ${tree.temperatureRange.endInclusive} derece arasında"
            treeHumadity.text = "Nem Oranı: ${tree.humidityRange.start} ile ${tree.humidityRange.endInclusive} % arasında"
            treeFeatures.text = tree.features

            binding.root.setOnClickListener {
                onItemClick(tree)
            }
        }

        private fun loadImageWithGlide(url: String, fallbackResource: Int) {
            binding.progressBar.visibility = View.VISIBLE
            Glide.with(binding.root.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(fallbackResource)
                .error(fallbackResource)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        Log.e("SearchAdapter", "Image load failed: ${e?.message}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.treeImg)
        }

        private fun generateTreeImage(tree: Tree) {
            imageGenerationJobs[tree.name]?.cancel()

            // Check if image URL exists in cache
            val normalizedName = normalizeTreeName(tree.name)
            if (imageUrlCache.containsKey(normalizedName)) {
                //görseli ürettiysek tekrar üretmicez search ederken üretiyordu eskiden
                val cachedUrl = imageUrlCache[normalizedName]
                if (cachedUrl != null) {
                    loadImageWithGlide(cachedUrl, tree.img)
                    return
                }
            }

            binding.progressBar.visibility = View.VISIBLE
            binding.treeImg.setImageResource(tree.img)

            val job = coroutineScope.launch {
                try {
                    delay(1000)
                    val prompt = buildString {
                        append("A high-quality illustration of a full-grown ${tree.name} tree. ")
                        append("This should be a clear and detailed botanical-style digital illustration. ")
                        append("The image should show the full structure of the tree, including trunk, branches, and leaves. ")
                        append("White background. No text, no watermark. Format: vertical. ")

                        when {
                            tree.temperatureRange.endInclusive > 25 -> append("This tree grows in warm climates like the Mediterranean. ")
                            tree.temperatureRange.start < 10 -> append("This tree is adapted to cool climates, such as Northern regions. ")
                            else -> append("This tree thrives in temperate zones. ")
                        }

                        when {
                            tree.humidityRange.endInclusive > 70 -> append("It is typically found in moist, water-rich environments. ")
                            tree.humidityRange.start < 30 -> append("It is drought-tolerant and prefers dry soil. ")
                            else -> append("It grows well in moderately humid areas. ")
                        }

                        if (tree.features.isNotBlank()) {
                            append("Important characteristics: ${tree.features}. ")
                        }

                        append("No humans, animals, or buildings. Botanical textbook style. 4K resolution.")
                    }

                    Log.d("SearchAdapter", "Generating image for: $prompt")

                    val request = ImageGenerationRequest(
                        prompt = prompt,
                        n = 1,
                        size = "512x512"
                    )

                    val response = withContext(Dispatchers.IO) { //Ağ işleminin nerden yaptığımızı belirttik
                        OpenAIClient.service.generateImage(request)
                    }

                    if (response.isSuccessful && response.body()?.data?.isNotEmpty() == true) {
                        val imageUrl = response.body()?.data?.first()?.url
                        withContext(Dispatchers.Main) {
                            if (imageUrl != null) {
                                // Cache the generated image URL
                                imageUrlCache[normalizedName] = imageUrl //url cashe eklencek

                                Log.d("SearchAdapter", "Image URL received: $imageUrl")
                                loadImageWithGlide(imageUrl, tree.img)

                                // Update the tree in the list with the new image URL
                                val position = adapterPosition
                                if (position != RecyclerView.NO_POSITION) {
                                    //adağter listesini güncelledik
                                    val updatedTree = tree.copy(imageUrl = imageUrl)
                                    treeList2 = treeList2.toMutableList().apply {
                                        set(position, updatedTree)
                                    }
                                    notifyItemChanged(position)
                                }
                            } else {
                                Log.e("SearchAdapter", "Image URL is null")
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    } else {
                        Log.e("SearchAdapter", "Error response: ${response.errorBody()?.string()}")
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SearchAdapter", "Error generating image", e)
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

            imageGenerationJobs[tree.name] = job
        }

        private fun isAIGeneratedTree(tree: Tree): Boolean {
            if (tree.imageUrl != null) {
                return false
            }

            return when (tree.img) {
                R.drawable.ic_olive,
                R.drawable.ic_oak,
                R.drawable.ic_pine,
                R.drawable.ic_poplar,
                R.drawable.ic_juniper,
                R.drawable.ic_willow,
                R.drawable.ic_walnut,
                R.drawable.ic_acacia,
                R.drawable.ic_chestnut -> false
                else -> true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(treeList2[position])
    }

    override fun getItemCount(): Int {
        Log.d("TreeAdapter", "Item count: ${treeList2.size}")
        return treeList2.size
    }

    private fun normalizeTreeName(name: String): String {
        return name.lowercase()
            .replace("ağacı", "")
            .replace("agaci", "")
            .trim()
            .replace(Regex("\\s+"), " ") // normalize spaces
            .replace("ı", "i")
            .replace("ç", "c")
            .replace("ş", "s")
            .replace("ğ", "g")
            .replace("ö", "o")
            .replace("ü", "u")
    }

    private fun areTreeNamesMatching(name1: String, name2: String): Boolean {
        val normalized1 = normalizeTreeName(name1)
        val normalized2 = normalizeTreeName(name2)

        // Check if one contains the other (for partial matches)
        return normalized1.contains(normalized2) || normalized2.contains(normalized1)
    }

    fun updateTreeList(newTreeList: List<Tree>) {
        val mappedList = newTreeList.map { newTree ->
            // Find existing tree with matching name
            val existingTree = treeList2.find { existingTree ->
                areTreeNamesMatching(existingTree.name, newTree.name)
            }

            val drawable = when (newTree.name.lowercase()) {
                "zeytin ağacı", "zeytin" -> R.drawable.ic_olive
                "meşe ağacı", "meşe" -> R.drawable.ic_oak
                "çam ağacı", "çam" -> R.drawable.ic_pine
                "kavak ağacı", "kavak" -> R.drawable.ic_poplar
                "ardıç ağacı", "ardıç" -> R.drawable.ic_juniper
                "söğüt ağacı", "söğüt" -> R.drawable.ic_willow
                "ceviz ağacı", "ceviz" -> R.drawable.ic_walnut
                "akasya ağacı", "akasya" -> R.drawable.ic_acacia
                "kestane ağacı", "kestane" -> R.drawable.ic_chestnut
                else -> R.drawable.ic_default_tree
            }

            // Check cache first
            val normalizedName = normalizeTreeName(newTree.name)
            val cachedImageUrl = imageUrlCache[normalizedName]

            newTree.copy(
                img = drawable,
                imageUrl = cachedImageUrl ?: existingTree?.imageUrl ?: newTree.imageUrl
            )
        }

        val diffCallback = object : DiffUtil.Callback() {
            //RecyclerView optimizasyonu yaptık sadece değişen itemlerin güncellenmesi için
            override fun getOldListSize() = treeList2.size
            override fun getNewListSize() = mappedList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldTree = treeList2[oldItemPosition]
                val newTree = mappedList[newItemPosition]
                return areTreeNamesMatching(oldTree.name, newTree.name)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList2[oldItemPosition] == mappedList[newItemPosition]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        treeList2 = mappedList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        //Adapterdan ayrılınca tüm coroutine işlemleri, görevler iptal edilcek
        super.onDetachedFromRecyclerView(recyclerView)
        coroutineScope.cancel()
        imageGenerationJobs.clear()
    }
}