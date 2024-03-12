package com.example.marvelapp.features.heroes.data.local.repository

import com.example.marvelapp.features.heroes.data.local.datasource.FavoriteLocalDataSource
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoriteRepository {
    fun getAll(): Flow<List<CharacterEntity>>
    suspend fun isFavorite(characterId: Int): Boolean
    suspend fun saveFavorite(characterEntity: CharacterEntity)
    suspend fun deleteFavorite(characterEntity: CharacterEntity)
}

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
): FavoriteRepository {

    override fun getAll(): Flow<List<CharacterEntity>> {
        return favoriteLocalDataSource.getAll()
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoriteLocalDataSource.isFavorite(characterId)
    }

    override suspend fun saveFavorite(characterEntity: CharacterEntity) {
        return favoriteLocalDataSource.save(characterEntity)
    }

    override suspend fun deleteFavorite(characterEntity: CharacterEntity) {
        return favoriteLocalDataSource.delete(characterEntity)
    }
}