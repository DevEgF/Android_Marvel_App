package com.example.marvelapp.features.favorites.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.marvelapp.features.favorites.data.FavoriteItem
import com.example.marvelapp.features.favorites.domain.GetFavoriteUseCase
import com.example.marvelapp.utils.MainCoroutineRule
import com.example.marvelapp.utils.factory.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class FavoritesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var favoritesViewModel: FavoritesViewModel
    @Mock
    private lateinit var useCase: GetFavoriteUseCase
    @Mock
    private lateinit var uiStateObserver: Observer<FavoritesViewModel.UiState>

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val favoriteItems = listOf(
        FavoriteItem(character.id,character.name, character.imageUrl)
    )

    @Before
    fun setup(){
        favoritesViewModel  = FavoritesViewModel(
            useCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            state.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with Success when getAll favorites`() =
        runTest {
            whenever(useCase.invoke(any()))
                .thenReturn(
                    flowOf(listOf(character))
                )

            favoritesViewModel.getAll()
            val uiState = favoritesViewModel.state.value

            assertEquals(uiState, FavoritesViewModel.UiState.ShowFavorite(favoriteItems))
        }

    @Test
    fun `should notify uiState with Empty when getAll favorites`() =
        runTest {

            whenever(useCase.invoke(any()))
                .thenReturn(
                    flowOf(emptyList())
                )

            favoritesViewModel.getAll()
            val uiState = favoritesViewModel.state.value

            assertEquals(uiState, FavoritesViewModel.UiState.ShowEmpty)
        }
}