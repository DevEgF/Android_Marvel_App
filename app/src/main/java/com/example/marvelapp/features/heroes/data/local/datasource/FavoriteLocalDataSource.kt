package com.example.marvelapp.features.heroes.data.local.datasource

import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.domain.entities.toFavoriteEntity
import com.example.marvelapp.framework.db.dao.FavoriteDao
import com.example.marvelapp.framework.db.entity.FavoriteEntity
import com.example.marvelapp.framework.db.entity.toCharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FavoriteLocalDataSource {
    fun getAll(): Flow<List<CharacterEntity>>
    suspend fun save(characterEntity: CharacterEntity)
    suspend fun delete(characterEntity: CharacterEntity)
}

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteLocalDataSource {

    override fun getAll(): Flow<List<CharacterEntity>> {
        return favoriteDao.loadFavorites().map {
            it.toCharacterEntity()
        }
    }

    override suspend fun save(characterEntity: CharacterEntity) {
        return favoriteDao.insertFavorite(characterEntity.toFavoriteEntity())
    }

    override suspend fun delete(characterEntity: CharacterEntity) {
        return favoriteDao.deleteFavorite(characterEntity.toFavoriteEntity())
    }
}