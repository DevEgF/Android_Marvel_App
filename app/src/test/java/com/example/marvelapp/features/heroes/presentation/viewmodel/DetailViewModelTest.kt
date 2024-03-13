package com.example.marvelapp.features.heroes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.marvelapp.R
import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.features.heroes.domain.entities.ComicEntity
import com.example.marvelapp.features.heroes.domain.usecase.AddFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.CheckFavoriteUseCase
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersDetailsUseCase
import com.example.marvelapp.features.heroes.domain.usecase.RemoveFavoriteUseCase
import com.example.marvelapp.features.heroes.presentation.livedata.FavoriteUiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.livedata.UiActionStateLiveData
import com.example.marvelapp.features.heroes.presentation.viewArgs.DetailViewArg
import com.example.marvelapp.utils.MainCoroutineRule
import com.example.marvelapp.utils.factory.CharacterFactory
import com.example.marvelapp.utils.factory.ComicFactory
import com.example.marvelapp.utils.factory.EventFactory
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
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    lateinit var getCharactersDetailsUseCase: GetCharactersDetailsUseCase

    @Mock
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    lateinit var checkFavoriteUseCase: CheckFavoriteUseCase

    @Mock
    lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiActionStateLiveData.UiState>

    @Mock
    private lateinit var favoriteUiStateObserver: Observer<FavoriteUiActionStateLiveData.UiState>

    private val characters = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(
            getCharactersDetailsUseCase,
            addFavoriteUseCase,
            mainCoroutineRule.testDispatcherProvider,
            checkFavoriteUseCase,
            removeFavoriteUseCase
        ).apply {
            categories.state.observeForever(uiStateObserver)
            favorite.state.observeForever(favoriteUiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() =
        runTest {
            whenever(getCharactersDetailsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            comics to events
                        )
                    )
                )

            detailViewModel.categories.load(characters.id)

            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSuccess = detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(2, categoriesParentList.size)

            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )

            assertEquals(
                R.string.details_events_category,
                categoriesParentList[1].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() =
        runTest {
            whenever(getCharactersDetailsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            comics to emptyList()
                        )
                    )
                )

            detailViewModel.categories.load(characters.id)

            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSuccess = detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)

            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() =
        runTest {
            whenever(getCharactersDetailsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<ComicEntity>() to events
                        )
                    )
                )

            detailViewModel.categories.load(characters.id)

            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSuccess = detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)

            assertEquals(
                R.string.details_events_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() =
        runTest {
            whenever(getCharactersDetailsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<ComicEntity>() to emptyList()
                        )
                    )
                )

            detailViewModel.categories.load(characters.id)

            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Empty>())
        }

    @Test
    fun `should notify uiState with Error from UiState when get character categories returns an exception`() =
        runTest {
            whenever(getCharactersDetailsUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(Throwable())
                    )
                )

            detailViewModel.categories.load(characters.id)

            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Error>())
        }

    @Test fun `should notify favorite_uiState with filled favorite icon when check favorite returns true`() =
        runTest {
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(true)
                    )
                )

            detailViewModel.favorite.checkFavorite(characters.id)

            verify(favoriteUiStateObserver).onChanged(
                isA<FavoriteUiActionStateLiveData.UiState.Icon>()
            )

            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon

            assertEquals(R.drawable.ic_favorite_checked, uiState.icon)
        }

    @Test
    fun `should notify favorite_uiState with not filled favorite icon when check favorite returns false`() =
        runTest {
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(false))
                )

            detailViewModel.favorite.checkFavorite(characters.id)

            verify(favoriteUiStateObserver).onChanged(
                isA<FavoriteUiActionStateLiveData.UiState.Icon>()
            )

            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon

            assertEquals(R.drawable.ic_favorite_unchecked, uiState.icon)

        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when current icon is unchecked`() =
        runTest {

            whenever(addFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(Unit)
                    )
                )


            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                favorite.update(
                    DetailViewArg(
                        characters.id,
                        characters.name,
                        characters.description,
                        characters.imageUrl
                    )
                )
            }


            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLiveData.UiState.Icon>())
            val uiState = detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_checked, uiState.icon)
        }

    @Test
    fun `should call remove and notify favorite_uiState with filled favorite icon when current icon is checked`() =
        runTest {

            whenever(removeFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(Unit)
                    )
                )

            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_checked
                favorite.update(
                    DetailViewArg(
                        characters.id,
                        characters.name,
                        characters.description,
                        characters.imageUrl
                    )
                )
            }

            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLiveData.UiState.Icon>())
            val uiState = detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_unchecked, uiState.icon)
        }
}