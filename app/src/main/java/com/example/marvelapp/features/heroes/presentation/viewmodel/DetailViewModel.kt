package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.features.heroes.domain.usecase.AddFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import com.example.marvelapp.features.heroes.presentation.livedata.FavoriteUiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.livedata.UiActionStateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    useCase: GetCharactersDetailsUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
): ViewModel() {

    val categories = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        useCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutinesDispatchers.main(),
        addFavoriteUseCase
    )

    init {
        favorite.setDefault()
    }
}