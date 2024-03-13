package com.example.marvelapp.features.heroes.data.response

import com.example.marvelapp.commons.data.ThumbnailResponse
import com.example.marvelapp.commons.data.getHttpsUrl
import com.example.marvelapp.features.heroes.domain.entities.EventEntity

data class EventResponse (
    val id: Int,
    val thumbnail: ThumbnailResponse
)

fun EventResponse.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        imageUrl = this.thumbnail.getHttpsUrl()

    )
}