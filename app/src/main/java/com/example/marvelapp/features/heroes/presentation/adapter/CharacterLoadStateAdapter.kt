package com.example.marvelapp.features.heroes.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.marvelapp.features.heroes.presentation.viewholder.CharacterLoadMoreStateViewHolder

class CharacterLoadStateAdapter(
   private val retry: () -> Unit,
): LoadStateAdapter<CharacterLoadMoreStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CharacterLoadMoreStateViewHolder.create(parent, retry)

    override fun onBindViewHolder(
        holder: CharacterLoadMoreStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}