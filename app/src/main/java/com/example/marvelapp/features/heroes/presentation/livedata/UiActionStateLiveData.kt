package com.example.marvelapp.features.heroes.presentation.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.extensions.watchStatus
import com.example.marvelapp.features.heroes.domain.entities.DetailChildVO
import com.example.marvelapp.features.heroes.domain.entities.DetailParentVO
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import kotlin.coroutines.CoroutineContext

class UiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val getCharactersDetailsUseCase: GetCharactersDetailsUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when(it) {
                is Action.Load -> {
                    getCharactersDetailsUseCase
                        .invoke(
                            GetCharactersDetailsUseCase.GetCategoriesParams(it.characterId)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = { data ->
                                val detailParentList = mutableListOf<DetailParentVO>()

                                val comics = data.first

                                if (comics.isNotEmpty()) {
                                    comics.map {
                                        DetailChildVO(it.id, it.imageUrl)
                                    }.also {
                                        detailParentList.add(
                                            DetailParentVO(
                                                R.string.details_comics_category,
                                                it
                                            )
                                        )
                                    }
                                }

                                val event = data.second

                                if(event.isNotEmpty()) {
                                    event.map {
                                        DetailChildVO(
                                            it.id,
                                            it.imageUrl
                                        )
                                    }.also {
                                        detailParentList.add(
                                            DetailParentVO(
                                                R.string.details_events_category,
                                                it
                                            )
                                        )
                                    }
                                }

                                if(detailParentList.isNotEmpty()) {
                                    emit(UiState.Success(detailParentList))
                                } else emit(UiState.Empty)
                            },
                            error = {
                                emit(UiState.Error)
                            }
                        )
                }
            }
        }
    }

    fun load(characterId: Int) {
        action.value = Action.Load(characterId)
    }

    sealed class UiState {
        object Loading: UiState()
        data class Success(val detailParentList: List<DetailParentVO>): UiState()
        object Error: UiState()
        object Empty: UiState()
    }

    sealed class Action {
        data class Load(val characterId: Int): Action()
    }
}