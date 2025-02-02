package com.example.marvelapp.utils.factory

import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

class CharacterFactory {
    fun create(hero: Hero) =
        when(hero) {
            Hero.ThreeDMan -> CharacterEntity(
                id = 1011334,
                name = "3-D Man",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
                description = ""
            )
            Hero.ABomb -> CharacterEntity(
                id = 1017100,
                name = "A-Bomb (HAS)",
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
                description = "",
            )
        }

    sealed class Hero {
        object ThreeDMan: Hero()
        object ABomb: Hero()
    }
}