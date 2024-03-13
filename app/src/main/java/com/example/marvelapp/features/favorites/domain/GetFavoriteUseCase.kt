package com.example.marvelapp.features.favorites.domain

import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.commons.utils.usecase.LocalUseCase
import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepository
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetFavoriteUseCase {
    suspend operator fun  invoke (params: Unit = Unit): Flow<List<CharacterEntity>>
}

class GetFavoriteUseCaseImpl @Inject constructor (
    private val favoritesRepository: FavoriteRepository,
    private val dispatchers: CoroutinesDispatchers
): LocalUseCase<Unit, List<CharacterEntity>>(),
    GetFavoriteUseCase {
    override suspend fun createFlowObservable(params: Unit): Flow<List<CharacterEntity>> {
        return withContext(dispatchers.io()){
            favoritesRepository.getAll()
        }
    }
}