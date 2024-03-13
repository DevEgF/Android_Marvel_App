package com.example.marvelapp.features.favorites.presentation.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.marvelapp.commons.presentation.viewholder.GenericViewHolder
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.features.favorites.data.FavoriteItem
import com.example.marvelapp.framework.imageloader.ImageLoader

class FavoriteViewHolder(
    itemBinding: ItemCharacterBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<FavoriteItem>(itemBinding) {

    private val textName: TextView = itemBinding.textName
    private val imageCharacter: ImageView = itemBinding.imageCharacters

    override fun bind(data: FavoriteItem) {
        textName.text = data.name
        imageLoader.load(imageCharacter, data.imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoriteViewHolder {
            val itemBinding = ItemCharacterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return FavoriteViewHolder(itemBinding, imageLoader)
        }
    }
}