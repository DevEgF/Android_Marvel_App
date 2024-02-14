package com.example.marvelapp.features.heroes.domain.entities

data class CharactersPaging (
    val offset: Int,
    val total: Int,
    val characters: List<CharacterEntity>
)