package com.farukayata.t_vac_kotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farukayata.t_vac_kotlin.R
import com.farukayata.t_vac_kotlin.databinding.ItemTreeBinding
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.util.loadImage

class TreeAdapter(
    private var treeList: List<Tree>,
    private val onItemClick: (Tree) -> Unit
) : RecyclerView.Adapter<TreeAdapter.TreeViewHolder>() {

    inner class TreeViewHolder(val binding: ItemTreeBinding) : RecyclerView.ViewHolder(binding.root) {
        val treeName = binding.treeName
        val treeTemp = binding.treeTemp
        val treeHumadity = binding.treeHumadity
        val treeFeatures = binding.treeFeatures

        fun bind(tree: Tree) {
            //open ai dan image alıncak alınmassa treeçimg kullancaz
            if (tree.imageUrl != null) {
                Glide.with(binding.root.context)
                    .load(tree.imageUrl)
                    .error(tree.img)
                    .into(binding.treeImg)
            } else {
                binding.treeImg.loadImage(tree.img)
            }

            treeName.text = "Ağaç Türü: ${tree.name}"
            treeTemp.text = "Sıcaklık: ${tree.temperatureRange.start} ile ${tree.temperatureRange.endInclusive} derece arasında"
            treeHumadity.text = "Nem Oranı: ${tree.humidityRange.start} ile ${tree.humidityRange.endInclusive} % arasında"
            treeFeatures.text = tree.features

            binding.root.setOnClickListener {
                onItemClick(tree)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {
        val binding = ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //XML layout dosyasını viewhoolder a bağladık
        return TreeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
        holder.bind(treeList[position])
    }

    override fun getItemCount(): Int {
        Log.d("TreeAdapter", "Item count: ${treeList.size}")
        return treeList.size
    }

    fun updateTreeList(newTreeList: List<Tree>) {
        val diffCallback = object : DiffUtil.Callback() {
            //yeni gelen listeyle önceki liste karşılaştırılarak yalnızca farklı olan itemler(şu anlık görsel) güncellenir böylece performans artar ve recyclerViewı yeniden çizmeyiz
            override fun getOldListSize() = treeList.size
            override fun getNewListSize() = newTreeList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList[oldItemPosition].name == newTreeList[newItemPosition].name
            //aynı ağaç ismine sahipse görselleri yada diğer itemleri alır

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                treeList[oldItemPosition] == newTreeList[newItemPosition]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        treeList = newTreeList
        diffResult.dispatchUpdatesTo(this)
    }
}