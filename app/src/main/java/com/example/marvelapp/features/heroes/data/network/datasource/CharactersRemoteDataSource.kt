package com.example.marvelapp.features.heroes.data.network.datasource

import com.example.marvelapp.features.heroes.data.response.toCharacterEntity
import com.example.marvelapp.features.heroes.data.response.toComicEntity
import com.example.marvelapp.features.heroes.data.response.toEventEntity
import com.example.marvelapp.features.heroes.domain.entities.CharactersPaging
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.entities.EventEntity
import com.example.marvelapp.framework.service.MarvelApi
import javax.inject.Inject

interface CharactersRemoteDataSource {
    suspend fun fetchCharacters(queries: Map<String, String>): CharactersPaging
    suspend fun fetchComics(characterId: Int): List<ComicEntity>
    suspend fun fetchStories(characterId: Int): List<EventEntity>
}

class CharactersRemoteDataSourceImpl @Inject constructor(
    private val marvelApi: MarvelApi
): CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharactersPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterEntity()
        }
        return CharactersPaging(
            data.offset,
            data.total,
            characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<ComicEntity> {
       return marvelApi.getComics(characterId).data.results.map {
           it.toComicEntity()
       }
    }

    override suspend fun fetchStories(characterId: Int): List<EventEntity> {
        return  marvelApi.getEvents(characterId).data.results.map {
            it.toEventEntity()
        }
    }
}