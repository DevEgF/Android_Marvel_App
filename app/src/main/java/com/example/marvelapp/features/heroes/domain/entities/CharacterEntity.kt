package com.example.marvelapp.features.heroes.domain.entities

data class CharacterEntity (
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String
)