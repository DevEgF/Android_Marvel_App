package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.entities.DetailChildVO
import com.example.marvelapp.features.heroes.domain.entities.DetailParentVO
import com.example.marvelapp.features.heroes.domain.entities.EventEntity
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GetCharactersDetailsUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getCharacterCategories(characterId: Int) = viewModelScope.launch {
        useCase.invoke(GetCharactersDetailsUseCase.GetComicsParams(characterId))
            .watchStatus()
    }

    private fun Flow<ResultStatus<Pair<List<ComicEntity>, List<EventEntity>>>>.watchStatus() =
        viewModelScope.launch {
            collect { status ->
                _uiState.value = when(status) {
                    ResultStatus.Loading -> UiState.Loading
                    is ResultStatus.Success -> {
                        val detailParentList = mutableListOf<DetailParentVO>()

                        val comics = status.data.first

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

                        val event = status.data.second

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
                            UiState.Success(detailParentList)
                        } else {
                            UiState.Empty
                        }

                    }
                    is ResultStatus.Error -> UiState.Error
                }
            }
        }

    sealed class UiState {
        object Loading: UiState()
        data class Success(val detailParentList: List<DetailParentVO>): UiState()
        object Error: UiState()
        object Empty: UiState()
    }
}