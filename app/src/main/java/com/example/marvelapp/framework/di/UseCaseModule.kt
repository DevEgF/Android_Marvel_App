package com.example.marvelapp.framework.di

import com.example.marvelapp.features.heroes.domain.usecase.AddFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.AddFavoriteUseCaseImpl
import com.example.marvelapp.features.heroes.domain.usecase.CheckFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.CheckFavoriteUseCaseImpl
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCaseImpl
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCaseImpl
import com.example.marvelapp.features.heroes.domain.usecase.RemoveFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.RemoveFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharacterUseCase(useCaseImpl: GetCharactersUseCaseImpl):GetCharactersUseCase
    @Binds
    fun bindGetComicsUseCase(useCaseImpl: GetCharactersDetailsUseCaseImpl): GetCharactersDetailsUseCase
    @Binds
    fun addFavoriteUseCase(useCaseImpl: AddFavoriteUseCaseImpl): AddFavoriteUseCase
    @Binds
    fun checkFavoriteUseCase(useCaseImpl: CheckFavoriteUseCaseImpl): CheckFavoriteUseCase
    @Binds
    fun removeFavoriteUseCase(useCaseImpl: RemoveFavoriteUseCaseImpl): RemoveFavoriteUseCase
}