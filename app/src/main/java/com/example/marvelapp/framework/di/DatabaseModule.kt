package com.example.marvelapp.framework.di

import android.content.Context
import androidx.room.Room
import com.example.marvelapp.commons.utils.Constants.APP_DATABASE_NAME
import com.example.marvelapp.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    ).build()
}