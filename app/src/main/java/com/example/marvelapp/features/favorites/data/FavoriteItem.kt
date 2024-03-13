package com.example.marvelapp.features.favorites.data

import com.example.marvelapp.commons.presentation.diffcallback.ListItem

data class FavoriteItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    override val key: Long = id.toLong()
): ListItem
