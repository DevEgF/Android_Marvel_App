package com.example.marvelapp.framework.di

import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCaseImpl
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCaseImpl
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
}