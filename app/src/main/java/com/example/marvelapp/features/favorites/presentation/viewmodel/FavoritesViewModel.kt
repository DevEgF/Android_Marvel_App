package com.example.marvelapp.features.favorites.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.features.favorites.data.FavoriteItem
import com.example.marvelapp.features.favorites.domain.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
):ViewModel() {
    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutinesDispatchers.main()) {
            when (action) {
                is Action.GetAll -> {
                    getFavoriteUseCase.invoke()
                        .catch {
                            emit(UiState.ShowEmpty)
                        }
                        .collect{
                            val itens = it.map { character ->
                                FavoriteItem(
                                    character.id,
                                    character.name,
                                    character.imageUrl
                                )
                            }
                            val uiState = if(itens.isNotEmpty()){
                                UiState.ShowFavorite(itens)
                            }else UiState.ShowEmpty
                            emit(uiState)
                        }
                }
            }
        }
    }

    fun getAll(){
        action.value = Action.GetAll
    }

    sealed class UiState {
        data class ShowFavorite(val favorite: List<FavoriteItem>): UiState()
        object ShowEmpty: UiState()
    }

    sealed class Action {
        object GetAll: Action()
    }
}