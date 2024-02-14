package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.commons.utils.usecase.UseCase
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.entities.EventEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCharactersDetailsUseCase {
    operator fun invoke(
        params: GetCategoriesParams
    ): Flow<ResultStatus<Pair<List<ComicEntity>, List<EventEntity>>>>
    data class GetCategoriesParams(val characterId: Int)
}

class GetCharactersDetailsUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
    private val dispatchers: CoroutinesDispatchers
): GetCharactersDetailsUseCase, UseCase<GetCharactersDetailsUseCase.GetCategoriesParams, Pair<List<ComicEntity>, List<EventEntity>>>(){

    override suspend fun doWork(
        params: GetCharactersDetailsUseCase.GetCategoriesParams
    ): ResultStatus<Pair<List<ComicEntity>, List<EventEntity>>> {
        return withContext(dispatchers.io()) {

            val comicsDeferred = async {
                repository.getComics(params.characterId)
            }
            val comics = comicsDeferred.await()

            val eventDeferred = async {
                repository.getEvents(params.characterId)
            }
            val event = eventDeferred.await()

            ResultStatus.Success(comics to event)
        }
    }
}