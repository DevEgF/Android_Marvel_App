package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.usecase.GetComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GetComicsUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getComics(characterId: Int) = viewModelScope.launch {
        useCase.invoke(GetComicsUseCase.GetComicsParams(characterId))
            .watchStatus()
    }

    private fun Flow<ResultStatus<List<ComicEntity>>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            _uiState.value = when(status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> UiState.Success(status.data)
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading: UiState()
        data class Success(val comics: List<ComicEntity>): UiState()
        object Error: UiState()
    }
}