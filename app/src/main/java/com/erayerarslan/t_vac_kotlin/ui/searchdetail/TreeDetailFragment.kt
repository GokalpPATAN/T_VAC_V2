package com.erayerarslan.t_vac_kotlin.ui.tree_detail

import com.erayerarslan.t_vac_kotlin.model.Plant
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.erayerarslan.t_vac_kotlin.R
import com.erayerarslan.t_vac_kotlin.databinding.FragmentTreeDetailBinding

class TreeDetailFragment : Fragment() {

    private var _binding: FragmentTreeDetailBinding? = null
    private val binding get() = _binding!!

    private val args: TreeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTreeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NavArgs ile gelen Plant objesi
        val plant: Plant = args.tree
        Log.d("TreeDetailFragment", "Loading plant: ${'$'}{plant.name}, img: ${'$'}{plant.img}")

        // Görseli Glide ile yükle veya placeholder göster
        if (plant.img.isNotBlank()) {
            binding.progressBar.visibility = View.VISIBLE
            Glide.with(this)
                .load(plant.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_default_tree)
                .error(R.drawable.ic_default_tree)
                .listener(object : com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable> {
                    override fun onLoadFailed(
                        e: com.bumptech.glide.load.engine.GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        Log.e("TreeDetailFragment", "Image load failed: ${'$'}{e?.message}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: android.graphics.drawable.Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.treeDetailImg)
        } else {
            binding.treeDetailImg.setImageResource(R.drawable.ic_default_tree)
        }

        // Metin alanlarını doldur
        binding.treeDetailName.text = plant.name

        binding.treeDetailTemp.text = if (plant.temperatureRange.isNotEmpty()) {
            "${plant.temperatureRange.first()} - ${plant.temperatureRange.last()} °C"
        } else {
            "Veri yok"
        }

        binding.treeDetailHumidity.text = if (plant.humidityRange.isNotEmpty()) {
            "${plant.humidityRange.first()} - ${plant.humidityRange.last()} %"
        } else {
            "Veri yok"
        }

        binding.treeDetailFeatures.text = plant.features
        binding.treeDetailPlantingInfo.text = plant.plantingInfo
        binding.treeDetailLocationNote.text = plant.locationNote
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
