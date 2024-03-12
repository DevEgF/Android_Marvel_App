package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelapp.commons.utils.Constants.LIMIT
import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val useCase: GetCharactersUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
): ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action
        .distinctUntilChanged()
        .switchMap { action ->
            when(action) {
                is Action.Search -> {
                    useCase(
                        GetCharactersUseCase.GetCharactersParams(action.query, getPageConfig())
                    ).cachedIn(viewModelScope).map {
                        UiState.SearchResults(it)
                    }.asLiveData(coroutinesDispatchers.main())
                }
            }
        }

    fun charactersPagingData(query: String): Flow<PagingData<CharacterEntity>> {
        return useCase(
            GetCharactersUseCase.GetCharactersParams(query, getPageConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig (
        pageSize = LIMIT
    )

    fun searchCharacters(query: String = "") {
        action.value = Action.Search(query)
    }

    sealed class UiState {
        data class SearchResults(val data: PagingData<CharacterEntity>): UiState()
    }

    sealed class Action {
        data class Search(val query: String): Action()
    }
}