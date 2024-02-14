package com.example.marvelapp.features.heroes.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.marvelapp.commons.utils.OnCharacterItemClick
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.presentation.viewholder.CharacterViewHolder
import com.example.marvelapp.framework.imageloader.ImageLoader
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
): PagingDataAdapter<CharacterEntity, CharacterViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent, imageLoader, onItemClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CharacterEntity,
                newItem: CharacterEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}