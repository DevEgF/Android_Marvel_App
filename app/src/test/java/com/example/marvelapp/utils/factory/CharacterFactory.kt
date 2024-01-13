package com.example.marvelapp.utils.factory

import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

class CharacterFactory {
    fun create(hero: Hero) =
        when(hero) {
            Hero.ThreeDMan -> CharacterEntity(
                "3-D Man",
                "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
                ""
            )
            Hero.ABomb -> CharacterEntity(
                "A-Bomb (HAS)",
                "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
                ""
            )
        }

    sealed class Hero {
        object ThreeDMan: Hero()
        object ABomb: Hero()
    }
}