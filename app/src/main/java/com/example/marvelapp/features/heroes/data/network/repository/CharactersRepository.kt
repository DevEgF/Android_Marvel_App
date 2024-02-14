package com.example.marvelapp.features.heroes.data.network.repository

import androidx.paging.PagingSource
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSource
import com.example.marvelapp.features.heroes.data.paging.CharactersPagingSource
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.entities.EventEntity
import javax.inject.Inject

interface CharactersRepository {
    fun getCharacters(query: String): PagingSource<Int, CharacterEntity>
    suspend fun getComics(characterId: Int): List<ComicEntity>
    suspend fun getEvents(characterId: Int): List<EventEntity>
}

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource
): CharactersRepository {
    override fun getCharacters(query: String): PagingSource<Int, CharacterEntity> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override suspend fun getComics(characterId: Int): List<ComicEntity> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<EventEntity> {
        return remoteDataSource.fetchStories(characterId)
    }
}