package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import com.example.marvelapp.commons.utils.extensions.watchStatus
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.entities.DetailChildVO
import com.example.marvelapp.features.heroes.domain.entities.DetailParentVO
import com.example.marvelapp.features.heroes.domain.entities.EventEntity
import com.example.marvelapp.features.heroes.domain.usecase.AddFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import com.example.marvelapp.features.heroes.presentation.livedata.FavoriteUiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.livedata.UiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.viewArgs.DetailViewArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
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