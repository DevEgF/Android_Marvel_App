package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.AppCoroutinesDispatchers
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
        params: GetComicsParams
    ): Flow<ResultStatus<Pair<List<ComicEntity>, List<EventEntity>>>>
    data class GetComicsParams(val characterId: Int)
}

class GetCharactersDetailsUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
    private val dispatchers: AppCoroutinesDispatchers
): GetCharactersDetailsUseCase, UseCase<GetCharactersDetailsUseCase.GetComicsParams, Pair<List<ComicEntity>, List<EventEntity>>>(){
    override suspend fun doWork(
        params: GetCharactersDetailsUseCase.GetComicsParams
    ): ResultStatus<Pair<List<ComicEntity>, List<EventEntity>>> {
        return withContext(dispatchers.io()) {

            val comicsDeferred = async {
                repository.getComics(params.characterId)
            }
            val comics = comicsDeferred.await()

            val eventDeferred = async {
                repository.getStories(params.characterId)
            }
            val event = eventDeferred.await()

            ResultStatus.Success(comics to event)
        }
    }
}