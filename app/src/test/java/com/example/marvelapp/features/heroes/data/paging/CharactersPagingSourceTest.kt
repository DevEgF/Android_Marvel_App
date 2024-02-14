package com.example.marvelapp.features.heroes.data.paging

import androidx.paging.PagingSource
import com.example.marvelapp.features.heroes.data.network.datasource.CharactersRemoteDataSource
import com.example.marvelapp.features.heroes.domain.entities.CharacterEntity
import com.example.marvelapp.utils.MainCoroutineRule
import com.example.marvelapp.utils.factory.CharacterFactory
import com.example.marvelapp.utils.factory.CharacterPagingFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var characterPagingSource: CharactersPagingSource

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource

    private val characterPagingFactory = CharacterPagingFactory()

    private val charactersFactory = CharacterFactory()

    @Before
    fun setup() {
        characterPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @Test
    fun `should return a success load result when load is called`() = runTest {
        whenever(remoteDataSource.fetchCharacters(any()))
            .thenReturn(characterPagingFactory.create())

        val result = characterPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                false
            )
        )
        val expected = listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABomb)
        )

        assertEquals(
            PagingSource.LoadResult.Page(
                data = expected,
                prevKey = null,
                nextKey = null
            ),
            result
        )

    }

    @Test
    fun `should return a error load resulta when load is called`() = runTest {
        val exception = RuntimeException()
        whenever(
            remoteDataSource.fetchCharacters(any())
        ).thenThrow(exception)

        val result = characterPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                false
            )
        )

        assertEquals(
            PagingSource.LoadResult.Error<Int, CharacterEntity>(exception),
            result
        )
    }
}