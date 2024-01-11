package com.example.marvelapp.features.heroes.domain.usecase

import androidx.paging.PagingConfig
import com.example.marvelapp.features.heroes.data.network.repository.CharactersRepository
import com.example.marvelapp.features.heroes.domain.usecase.GetCharactersUseCase.GetCharactersParams
import com.example.marvelapp.utils.MainCoroutineRule
import com.example.marvelapp.utils.factory.CharacterFactory
import com.example.marvelapp.utils.factory.PagingFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingSource = PagingFactory().create(listOf(hero))

    @Mock
    lateinit var charactersRepository: CharactersRepository

    @Before
    fun setup() {
        getCharactersUseCase = GetCharactersUseCaseImpl(charactersRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            whenever(
                charactersRepository.getCharacters("")
            ).thenReturn(fakePagingSource)

            val result =
                getCharactersUseCase.invoke(GetCharactersParams("", PagingConfig(20)))

            verify(charactersRepository).getCharacters("")

            assertNotNull(result.first())
        }
}