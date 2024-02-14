package com.example.marvelapp.features.heroes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemParentDetailBinding
import com.example.marvelapp.features.heroes.domain.entities.DetailParentVO
import com.example.marvelapp.framework.imageloader.ImageLoader

class DetailParentAdapter(
    private val detailParentList: List<DetailParentVO>,
    private val imageLoader: ImageLoader
): RecyclerView.Adapter<DetailParentAdapter.DetailParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailParentViewHolder {
        return DetailParentViewHolder.create(parent, imageLoader)
    }

    override fun onBindViewHolder(holder: DetailParentViewHolder, position: Int) {
        holder.bind(detailParentList[position])
    }

    override fun getItemCount() = detailParentList.size

    class DetailParentViewHolder(
        itemBinding: ItemParentDetailBinding,
        private val imageLoader: ImageLoader
    ): RecyclerView.ViewHolder(itemBinding.root) {

        private val textItemCategory: TextView = itemBinding.textItemCategory
        private val recyclerChildDetail: RecyclerView = itemBinding.recyclerChildDetail

        fun bind(detailParentVO: DetailParentVO){
            textItemCategory.text = itemView.context.getString(detailParentVO.categoryStringResId)

            recyclerChildDetail.run {
                setHasFixedSize(true)
                adapter = DetailChildAdapter(
                    detailParentVO.detailChildList,
                    imageLoader
                )
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                imageLoader: ImageLoader
            ): DetailParentViewHolder {
                val itemBinding = ItemParentDetailBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return DetailParentViewHolder(itemBinding,imageLoader)
            }
        }
    }
}