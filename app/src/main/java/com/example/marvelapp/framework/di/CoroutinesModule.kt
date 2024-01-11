package com.example.marvelapp.framework.di

import com.example.marvelapp.commons.utils.AppCoroutinesDispatchers
import com.example.marvelapp.commons.utils.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}