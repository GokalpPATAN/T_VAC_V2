package com.farukayata.t_vac_kotlin.ui.tree_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.farukayata.t_vac_kotlin.databinding.FragmentTreeDetailBinding
import com.farukayata.t_vac_kotlin.util.loadImage

class TreeDetailFragment : Fragment() {

    private var _binding: FragmentTreeDetailBinding? = null
    private val binding get() = _binding!!

    private val args: TreeDetailFragmentArgs by navArgs() // SafeArgs ile veriyi alıyoruz

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTreeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tree = args.tree // SafeArgs ile gelen Tree nesnesi

        binding.treeDetailImg.loadImage(tree.img)
        binding.treeDetailName.text = "Ağaç Türü: ${tree.name}"
        binding.treeDetailTemp.text = "Sıcaklık: ${tree.temperatureRange.start} - ${tree.temperatureRange.endInclusive} °C"
        binding.treeDetailHumidity.text = "Nem: ${tree.humidityRange.start} - ${tree.humidityRange.endInclusive} %"
        binding.treeDetailFeatures.text = "Özellikler: ${tree.features}"
        binding.treeDetailPlantingInfo.text = "Yetiştirme Bilgisi: ${tree.plantingInfo}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
