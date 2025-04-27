package com.farukayata.t_vac_kotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.farukayata.t_vac_kotlin.databinding.ItemTreeBinding
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.model.treeList
import com.farukayata.t_vac_kotlin.util.loadImage

class TreeAdapter(
    private var treeList: List<Tree>,
    private val onItemClick: (Tree) -> Unit//search sayfasında card itemlere tıklamak için
) : RecyclerView.Adapter<TreeAdapter.TreeViewHolder>() {

    /*
    inner class TreeViewHolder(val binding: ItemTreeBinding) : RecyclerView.ViewHolder(binding.root) {
        val treeName = binding.treeName
        val treeTemp = binding.treeTemp
        val treeHumadity = binding.treeHumadity
        val treeFeatures = binding.treeFeatures
    }
    */
    inner class TreeViewHolder(val binding: ItemTreeBinding) : RecyclerView.ViewHolder(binding.root) {
        val treeName = binding.treeName
        val treeTemp = binding.treeTemp
        val treeHumadity = binding.treeHumadity
        val treeFeatures = binding.treeFeatures

        fun bind(tree: Tree) {
            binding.treeImg.loadImage(tree.img)
            treeName.text = "Ağaç Türü: ${tree.name}"
            treeTemp.text = "Sıcaklık: ${tree.temperatureRange.start} ile ${tree.temperatureRange.endInclusive} derece arasında"
            treeHumadity.text = "Nem Oranı: ${tree.humidityRange.start} ile ${tree.humidityRange.endInclusive} % arasında"
            treeFeatures.text = tree.features

            binding.root.setOnClickListener {
                onItemClick(tree)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeAdapter.TreeViewHolder {
        val binding = ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TreeViewHolder(binding)
    }

    /*
    override fun onBindViewHolder(holder: TreeAdapter.TreeViewHolder, position: Int) {
        val tree = treeList[position]
        holder.binding.treeImg.loadImage(tree.img)
        holder.treeName.text = "Ağaç Türü: ${tree.name}"
        holder.treeTemp.text = "Sıcaklık: ${tree.temperatureRange.start} ile ${tree.temperatureRange.endInclusive} derece arasında"
        holder.treeHumadity.text = "Nem Oranı: ${tree.humidityRange.start} ile ${tree.humidityRange.endInclusive} % arasında"
        holder.treeFeatures.text = tree.features
    }
    */
    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
        holder.bind(treeList[position])
    }

    override fun getItemCount(): Int {
        Log.d("TreeAdapter", "Item count: ${treeList.size}")
        return treeList.size // Liste boyutunu doğru şekilde döndürdüğünden emin olun
    }

    fun updateTreeList(newTreeList: List<Tree>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = treeList.size
            override fun getNewListSize() = newTreeList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList[oldItemPosition].name == newTreeList[newItemPosition].name

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList[oldItemPosition] == newTreeList[newItemPosition]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        treeList = newTreeList
        diffResult.dispatchUpdatesTo(this)
    }

}