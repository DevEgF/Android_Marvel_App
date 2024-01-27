package com.example.marvelapp.features.heroes.data.response

import com.example.marvelapp.commons.data.network.response.ThumbnailResponse
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity

data class StoriesResponse (
    val id: Int,
    val thumbnail: ThumbnailResponse
)

fun StoriesResponse.toStoriesEntity(): ComicEntity {
    return ComicEntity(
        id = this.id,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http", "https")
    )
}