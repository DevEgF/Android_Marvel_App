package com.example.marvelapp.features.heroes.domain.entities

import com.example.marvelapp.framework.db.entity.FavoriteEntity

data class CharacterEntity (
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String
)

fun CharacterEntity.toFavoriteEntity() =
    FavoriteEntity(
        id,
        name,
        imageUrl,
        description
    )