package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.commons.utils.usecase.UseCase
import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepository
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddFavoriteUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(
        val characterId: Int, val name: String, val imageUrl: String, val description: String
    )
}

class AddFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val dispatchers: CoroutinesDispatchers
): UseCase<AddFavoriteUseCase.Params, Unit>(), AddFavoriteUseCase {
    override suspend fun doWork(params: AddFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()){
            favoriteRepository.saveFavorite(
                CharacterEntity(params.characterId, params.name, params.imageUrl, params.description)
            )
            ResultStatus.Success(Unit)
        }
    }
}