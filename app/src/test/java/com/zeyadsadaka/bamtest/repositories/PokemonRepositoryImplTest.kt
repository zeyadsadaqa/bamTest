package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.database.PokemonDatabase
import com.zeyadsadaka.bamtest.network.APIException
import com.zeyadsadaka.bamtest.network.AppAPI
import com.zeyadsadaka.bamtest.repositories.dto.Ability
import com.zeyadsadaka.bamtest.repositories.dto.AbilityContainer
import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import com.zeyadsadaka.bamtest.repositories.dto.Sprites
import com.zeyadsadaka.bamtest.ui.screens.mainscreen.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryImplTest {

    @Mock
    private lateinit var apiClient: AppAPI

    @Mock
    private lateinit var pokemonDatabase: PokemonDatabase

    private lateinit var sut: PokemonRepositoryImpl

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        sut = PokemonRepositoryImpl(
            apiClient,
            pokemonDatabase,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPokemons_whenAPICallSucceeds_shouldInsertAndReturnPokemons() = runTest {

        val pokemonListResponse = PokemonList(
            count = 2L,
            results = listOf(
                Pokemon("bulbasaur"),
                Pokemon("charmander")
            )
        )

        `when`(apiClient.getPokemons())
            .thenReturn(Response.success(pokemonListResponse))

        // Call function under test
        val actualPokemons = sut.getAllPokemons()
        advanceUntilIdle()

        // Verify functions got called
        Mockito.verify(apiClient).getPokemons()
        Mockito.verify(pokemonDatabase)
            .insertAllPokemons(pokemonListResponse.results)

        // Assert that expected pokemons are returned
        assertEquals(pokemonListResponse.results, actualPokemons.results)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = APIException::class)
    fun getAllPokemons_whenAPICallFails_shouldReturnDataFromDB() = runTest {
        val dbData = PokemonList(
            2L,
            listOf(
                Pokemon("bulbasaur"),
                Pokemon("charmander")
            )
        )
        given(apiClient.getPokemons())
            .willAnswer { throw APIException() }

        // Simulate database operations
        `when`(pokemonDatabase.getAllPokemons())
            .thenReturn(dbData)

        // Call function under test
        val actualPokemons = sut.getAllPokemons()
        advanceUntilIdle()

        // Verify API call didn't happen
        Mockito.verify(apiClient, never()).getPokemons()

        // Assert that expected pokemons are returned
        assertEquals(actualPokemons, dbData)
    }

    @Test
    fun getPokemonDetails_whenAPICallSucceeds_shouldInsertAndReturnDetails() = runTest {

        val pokemonDetailsResponse =
            PokemonDetails(
                name = "pokemon 1",
                sprites = Sprites(""),
                abilities = listOf(
                    AbilityContainer(Ability("ability1")),
                    AbilityContainer(Ability("ability2")),
                ),
            )

        `when`(apiClient.getPokemonDetails("bulbasaur"))
            .thenReturn(Response.success(pokemonDetailsResponse))

        // Call function under test
        val actualPokemonDetails =
            sut.getPokemonDetails("bulbasaur")


        Mockito.verify(apiClient).getPokemonDetails("bulbasaur")
        Mockito.verify(pokemonDatabase).insertPokemon(pokemonDetailsResponse)

        // Assert that expected pokemon details are returned
        assertEquals(pokemonDetailsResponse, actualPokemonDetails)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = APIException::class)
    fun getPokemonDetails_whenAPICallFails_shouldReturnDataFromDB() = runTest {
        val dbData =
            PokemonDetails(
                name = "pokemon 1",
                sprites = Sprites(""),
                abilities = listOf(
                    AbilityContainer(Ability("ability1")),
                    AbilityContainer(Ability("ability2")),
                ),
            )

        given(apiClient.getPokemonDetails("pokemon 1"))
            .willAnswer { throw APIException() }

        // Simulate database operations
        `when`(pokemonDatabase.getPokemon("pokemon 1"))
            .thenReturn(dbData)

        // Call function under test
        val actualPokemons = sut.getPokemonDetails("pokemon 1")
        advanceUntilIdle()

        // Verify API call didn't happen
        Mockito.verify(apiClient, never()).getPokemonDetails("pokemon 1")

        // Assert that expected pokemons are returned
        assertEquals(actualPokemons, dbData)
    }
}
