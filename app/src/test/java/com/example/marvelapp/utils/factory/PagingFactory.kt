package com.example.marvelapp.utils.factory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

class PagingFactory {

    fun create(heroes: List<CharacterEntity>) = object : PagingSource<Int, CharacterEntity>(){
        override fun getRefreshKey(state: PagingState<Int, CharacterEntity>) = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
            return LoadResult.Page(
                data =  heroes,
                prevKey = null,
                nextKey = 20
            )
        }
    }
}