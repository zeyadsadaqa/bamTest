package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import com.zeyadsadaka.bamtest.database.KeyValueConstants
import com.zeyadsadaka.bamtest.database.KeyValueStore
import com.zeyadsadaka.bamtest.network.APIException
import com.zeyadsadaka.bamtest.repositories.PokemonRepository
import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.times

@RunWith(MockitoJUnitRunner::class)
class MainScreenViewModelTest {
    @Mock
    private lateinit var mockPokemonRepository: PokemonRepository

    @Mock
    private lateinit var mockKeyValueStore: KeyValueStore

    private lateinit var viewModel: MainScreenViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = MainScreenViewModel(
            mockPokemonRepository,
            mockKeyValueStore,
        )
    }

    @Test
    fun getPokemons_whenStart_shouldUpdateUiStateWithLoading() {
        // Call function under test
        viewModel.getPokemons()
        // Verify that the UI state in Loading state
        assertEquals(viewModel.uiState.value, MainScreenUiState.Loading)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPokemons_whenRepositorySucceeds_shouldUpdateUiState() = runTest {
        // Create mock response from repository
        val pokemonList = listOf(
            Pokemon("bulbasaur"),
            Pokemon("charmander")
        )

        `when`(mockPokemonRepository.getAllPokemons())
            .thenReturn(
                PokemonList(
                    pokemonList.size.toLong(),
                    pokemonList
                )
            )


        val isDarkTheme = true
        `when`(
            mockKeyValueStore.getBooleanFlow(KeyValueConstants.IS_DARK_THEME_KEY)
        ).thenReturn(MutableStateFlow(isDarkTheme))

        // Call function under test
        viewModel.getPokemons()
        advanceUntilIdle()

        // Verify method was called
        verify(mockKeyValueStore, times(2))
            .getBooleanFlow(KeyValueConstants.IS_DARK_THEME_KEY)
        // Assert UI State
        assertEquals(viewModel.uiState.value, MainScreenUiState.Content(pokemonList, isDarkTheme))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = APIException::class)
    fun getPokemons_whenRepositoryFails_shouldUpdateUiState() = runTest {


        given(mockPokemonRepository)
            .willAnswer { throw APIException() }

        val isDarkTheme = true
        `when`(
            mockKeyValueStore.getBooleanFlow(KeyValueConstants.IS_DARK_THEME_KEY)
        ).thenReturn(MutableStateFlow(isDarkTheme))

        // Call function under test
        viewModel.getPokemons()
        advanceUntilIdle()
        // Assert UI State
        assertEquals(viewModel.uiState.value, MainScreenUiState.Error)
    }

    @Test
    fun filterPokemons_shouldFilterPokemons() {
        val pokemon = Pokemon("bulbasaur")

        // Filter correct pokemons
        val shouldFiltered1 = viewModel.filterPokemons(pokemon, "b")
        assertEquals(true, shouldFiltered1)

        // Filter incorrect pokemons
        val shouldNotFiltered = viewModel.filterPokemons(pokemon, "c")
        assertEquals(false, shouldNotFiltered)

        // Get all pokemons
        val shouldNotFiltered2 = viewModel.filterPokemons(pokemon, "ALL")
        assertEquals(false, shouldNotFiltered2)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule constructor(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}