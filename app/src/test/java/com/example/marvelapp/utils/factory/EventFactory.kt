package com.example.marvelapp.utils.factory

import com.example.marvelapp.features.heroes.domain.entities.EventEntity

class EventFactory {
    fun create(event: FakeEvent) = when (event) {
        FakeEvent.FakeEvent1 -> EventEntity(
            id = 1,
            imageUrl = "http://fakeurl.jpg"
        )
    }

    sealed class FakeEvent {
        object FakeEvent1 : FakeEvent()
    }
}