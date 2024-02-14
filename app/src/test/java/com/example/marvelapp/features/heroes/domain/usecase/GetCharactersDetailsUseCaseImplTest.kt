package com.example.marvelapp.features.heroes.domain.usecase

import com.example.marvelapp.commons.utils.status.ResultStatus
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.utils.MainCoroutineRule
import com.example.marvelapp.utils.factory.CharacterFactory
import com.example.marvelapp.utils.factory.ComicFactory
import com.example.marvelapp.utils.factory.EventFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class GetCharactersDetailsUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getCharactersDetailsUseCase: GetCharactersDetailsUseCase

    @Mock
    lateinit var charactersRepository: CharactersRepository

    private val characters = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setup() {
        getCharactersDetailsUseCase = GetCharactersDetailsUseCaseImpl(
            charactersRepository,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {
            whenever(charactersRepository.getComics(characters.id)).thenReturn(comics)
            whenever(charactersRepository.getEvents(characters.id)).thenReturn(events)

            val result = getCharactersDetailsUseCase.invoke(
                GetCharactersDetailsUseCase.GetCategoriesParams(characters.id)
            )

            val resultList = result.toList()

            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStatus when get events requests return error`() =
        runTest {
            whenever(charactersRepository.getEvents(characters.id)).thenAnswer{ throw Throwable() }

            val result = getCharactersDetailsUseCase.invoke(
                GetCharactersDetailsUseCase.GetCategoriesParams(characters.id)
            )

            val resultList = result.toList()

            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }

    @Test
    fun `should return Error from ResultStatus when get comics requests return error`() =
        runTest {
            whenever(charactersRepository.getComics(characters.id)).thenAnswer{ throw Throwable() }

            val result = getCharactersDetailsUseCase.invoke(
                GetCharactersDetailsUseCase.GetCategoriesParams(characters.id)
            )

            val resultList = result.toList()

            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }
}