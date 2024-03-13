package com.example.marvelapp.framework.di

import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepository
import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepositoryImpl
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun bindFavoriteRepository(repository: FavoriteRepositoryImpl): FavoriteRepository
}