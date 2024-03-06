package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.extensions.watchStatus
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.entities.DetailChildVO
import com.example.marvelapp.features.heroes.domain.entities.DetailParentVO
import com.example.marvelapp.features.heroes.domain.entities.EventEntity
import com.example.marvelapp.features.heroes.domain.usecase.AddFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import com.example.marvelapp.features.heroes.presentation.viewArgs.DetailViewArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GetCharactersDetailsUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _favoriteUiState = MutableLiveData<FavoriteUiState>()
    val favoriteUiState: LiveData<FavoriteUiState> get() = _favoriteUiState

    init {
        _favoriteUiState.value = FavoriteUiState.FavoriteIcon(R.drawable.ic_favorite_unchecked)
    }

    fun getCharacterCategories(characterId: Int) = viewModelScope.launch {
        useCase.invoke(GetCharactersDetailsUseCase.GetCategoriesParams(characterId))
            .watchStatus(
                loading = {
                    _uiState.value = UiState.Loading
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

                    _uiState.value = if(detailParentList.isNotEmpty()) {
                        UiState.Success(detailParentList)
                    } else UiState.Empty
                },
                error = {
                    _uiState.value = UiState.Error
                }
            )
    }

    fun updateFavorite(detailViewArg: DetailViewArg) = viewModelScope.launch {
        detailViewArg.run {
            addFavoriteUseCase.invoke(
                AddFavoriteUseCase.Params(
                    characterId,
                    name,
                    imageUrl,
                    description
                )
            ).watchStatus(
                loading = {
                    _favoriteUiState.value = FavoriteUiState.Loading
                },
                success = {
                    _favoriteUiState.value = FavoriteUiState.FavoriteIcon(R.drawable.ic_favorite_checked)
                },
                error = {
                }
            )
        }
    }

    sealed class UiState {
        object Loading: UiState()
        data class Success(val detailParentList: List<DetailParentVO>): UiState()
        object Error: UiState()
        object Empty: UiState()
    }

    sealed class FavoriteUiState {
        object Loading: FavoriteUiState()
        class FavoriteIcon(val icon: Int) : FavoriteUiState()
    }
}