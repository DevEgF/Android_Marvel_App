package com.example.marvelapp.features.heroes.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelapp.commons.utils.usecase.PagingUseCase
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCase.GetCharactersParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCharactersUseCase {
    operator fun  invoke (params: GetCharactersParams): Flow<PagingData<CharacterEntity>>
    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)
}

class GetCharactersUseCaseImpl @Inject constructor (
    private val repository: CharactersRepository
): PagingUseCase<GetCharactersParams, CharacterEntity>(),
    GetCharactersUseCase {

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<CharacterEntity>> {
        val pagingSource = repository.getCharacters(params.query)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }
}