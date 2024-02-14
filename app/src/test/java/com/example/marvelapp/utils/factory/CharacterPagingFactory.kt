package com.example.marvelapp.utils.factory

import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.domain.entities.CharactersPaging

class CharacterPagingFactory {

    fun create() = CharactersPaging(
        offset = 0,
        total = 0,
        characters = listOf(
            CharacterEntity(
                id = 1011334,
                name = "3-D Man",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
                description = "",
            ),
            CharacterEntity(
                id = 1017100,
                name = "A-Bomb (HAS)",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
                description = "",
            )
        )
    )
}