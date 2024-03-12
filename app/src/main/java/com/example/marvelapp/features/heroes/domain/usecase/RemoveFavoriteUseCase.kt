package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.commons.utils.usecase.UseCase
import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepository
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoveFavoriteUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(
        val characterId: Int, val name: String, val imageUrl: String, val description: String
    )
}

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val dispatchers: CoroutinesDispatchers
): UseCase<RemoveFavoriteUseCase.Params, Unit>(), RemoveFavoriteUseCase {
    override suspend fun doWork(params: RemoveFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()){
            favoriteRepository.deleteFavorite(
                CharacterEntity(params.characterId, params.name, params.imageUrl, params.description)
            )
            ResultStatus.Success(Unit)
        }
    }
}