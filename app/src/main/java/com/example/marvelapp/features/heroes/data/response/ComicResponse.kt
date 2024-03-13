package com.example.marvelapp.features.heroes.data.response

import com.example.marvelapp.commons.data.ThumbnailResponse
import com.example.marvelapp.commons.data.getHttpsUrl
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity

data class ComicResponse(
    val id: Int,
    val thumbnail: ThumbnailResponse
)

fun ComicResponse.toComicEntity(): ComicEntity {
    return ComicEntity(
        id = this.id,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}
