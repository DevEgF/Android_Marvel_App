package com.example.marvelapp.utils.factory

import com.example.marvelapp.features.heroes.domain.entities.ComicEntity

class ComicFactory {
    fun create(comic: FakeComic) = when(comic) {
        FakeComic.FakeComic1 -> ComicEntity(
            id = 2211506,
            imageUrl = "http://fakecomigurl.jpg"
        )
    }

    sealed class FakeComic {
        object FakeComic1 : FakeComic()
    }
}

