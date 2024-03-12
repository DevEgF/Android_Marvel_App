package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.commons.utils.usecase.UseCase
import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CheckFavoriteUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Boolean>>
    data class Params(val characterId: Int)
}

class CheckFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val dispatchers: CoroutinesDispatchers
): UseCase<CheckFavoriteUseCase.Params, Boolean>(), CheckFavoriteUseCase {

    override suspend fun doWork(params: CheckFavoriteUseCase.Params): ResultStatus<Boolean> {
       return withContext(dispatchers.io()) {
            val isFavorite = favoriteRepository.isFavorite(params.characterId)
            ResultStatus.Success(isFavorite)
        }
    }
}