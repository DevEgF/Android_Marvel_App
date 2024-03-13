package com.example.marvelapp.features.favorites.domain

import com.example.marvelapp.features.heroes.data.local.repository.FavoriteRepository
import com.example.marvelapp.utils.MainCoroutineRule
import com.example.marvelapp.utils.factory.CharacterFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
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
class GetFavoriteUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @Mock
    lateinit var favoriteRepository: FavoriteRepository

    private val characters = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    @Before
    fun setUp(){
        getFavoriteUseCase = GetFavoriteUseCaseImpl(
            favoriteRepository,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {
            whenever(favoriteRepository.getAll())
                .thenReturn(
                    flowOf(
                        listOf(
                            characters
                        )
                    )
                )

            getFavoriteUseCase.invoke(Unit)
        }

}