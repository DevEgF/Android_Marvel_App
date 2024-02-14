package com.example.marvelapp.features.heroes.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp.commons.utils.Constants.LIMIT
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSource
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val query: String
): PagingSource<Int, CharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        return try{

            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }

            val characterPaging = remoteDataSource.fetchCharacters(queries)

            val responseOffset = characterPaging.offset
            val totalCharacters = characterPaging.total

            LoadResult.Page(
                data = characterPaging.characters,
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }
}