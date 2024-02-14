package com.example.marvelapp.features.heroes.presentation.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.OnCharacterItemClick
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.framework.imageloader.ImageLoader

class CharacterViewHolder(
    itemCharacterBinding: ItemCharacterBinding,
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
): RecyclerView.ViewHolder(itemCharacterBinding.root) {

    private val textName = itemCharacterBinding.textName
    private val imageCharacter = itemCharacterBinding.imageCharacters

    fun bind(character: CharacterEntity) {
        textName.text = character.name
        imageCharacter.transitionName = character.name

        imageLoader.load(
            imageCharacter,
            character.imageUrl
        )

        itemView.setOnClickListener {
            onItemClick.invoke(character, character.description, imageCharacter)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            onItemClick: OnCharacterItemClick
        ): CharacterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parent, false)
            return CharacterViewHolder(itemBinding, imageLoader, onItemClick)
        }
    }
}