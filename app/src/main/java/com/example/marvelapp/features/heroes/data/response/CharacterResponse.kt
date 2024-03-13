package com.example.marvelapp.features.heroes.data.response

import com.example.marvelapp.commons.data.ThumbnailResponse
import com.example.marvelapp.commons.data.getHttpsUrl
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}
