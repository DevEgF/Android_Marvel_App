package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.commons.utils.usecase.UseCase
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetComicsUseCase {
    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<List<ComicEntity>>>
    data class GetComicsParams(val characterId: Int)
}

class GetComicsUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository
): GetComicsUseCase, UseCase<GetComicsUseCase.GetComicsParams, List<ComicEntity>>(){
    override suspend fun doWork(
        params: GetComicsUseCase.GetComicsParams
    ): ResultStatus<List<ComicEntity>> {
        val comics = repository.getComics(params.characterId)
        return ResultStatus.Success(comics)
    }
}