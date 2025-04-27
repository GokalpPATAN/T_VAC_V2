package com.farukayata.t_vac_kotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.farukayata.t_vac_kotlin.databinding.ItemTreeBinding
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.util.loadImage
import com.farukayata.t_vac_kotlin.R

class SearchAdapter (treeList: List<Tree>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var treeList2: List<Tree> = treeList
    inner class SearchViewHolder(val binding: ItemTreeBinding) : RecyclerView.ViewHolder(binding.root) {
        val treeName = binding.treeName
        val treeTemp = binding.treeTemp
        val treeHumadity = binding.treeHumadity
        val treeFeatures = binding.treeFeatures
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        val binding = ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val tree = treeList2[position]
        holder.binding.treeImg.loadImage(tree.img)
        holder.treeName.text = "Ağaç Türü: ${tree.name}"
        holder.treeTemp.text = "Sıcaklık: ${tree.temperatureRange.start} ile ${tree.temperatureRange.endInclusive} derece arasında"
        holder.treeHumadity.text = "Nem Oranı: ${tree.humidityRange.start} ile ${tree.humidityRange.endInclusive} % arasında"
        holder.treeFeatures.text = tree.features
    }

    override fun getItemCount(): Int {
        Log.d("TreeAdapter", "Item count: ${treeList2.size}")
        return treeList2.size // Liste boyutunu doğru şekilde döndürdüğünden emin olun
    }

    fun updateTreeList(newTreeList: List<Tree>) {
        // AI'dan gelen ağaçlara uygun görsel eşlemesi yap
        val mappedList = newTreeList.map { tree ->
            val drawable = when (tree.name.lowercase()) {
                "zeytin" -> R.drawable.ic_olive
                "meşe" -> R.drawable.ic_oak
                "çam" -> R.drawable.ic_pine
                "kavak" -> R.drawable.ic_poplar
                "ardıç" -> R.drawable.ic_juniper
                "söğüt" -> R.drawable.ic_willow
                "ceviz" -> R.drawable.ic_walnut
                "akasya" -> R.drawable.ic_acacia
                "kestane" -> R.drawable.ic_chestnut
                else -> R.drawable.ic_default_tree // varsayılan ikon
            }
            tree.copy(img = drawable)
        }

        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = treeList2.size
            override fun getNewListSize() = mappedList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList2[oldItemPosition].name == mappedList[newItemPosition].name

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList2[oldItemPosition] == mappedList[newItemPosition]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        treeList2 = mappedList
        diffResult.dispatchUpdatesTo(this)
    }

    /*
    fun updateTreeList(newTreeList: List<Tree>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = treeList2.size
            override fun getNewListSize() = newTreeList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList2[oldItemPosition].name == newTreeList[newItemPosition].name

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList2[oldItemPosition] == newTreeList[newItemPosition]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        treeList2 = newTreeList
        diffResult.dispatchUpdatesTo(this)
    }
    */

}