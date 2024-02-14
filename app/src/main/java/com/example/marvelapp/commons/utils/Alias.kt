package com.example.marvelapp.commons.utils

import android.view.View
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

typealias OnCharacterItemClick = (character: CharacterEntity, description: String, view: View) -> Unit