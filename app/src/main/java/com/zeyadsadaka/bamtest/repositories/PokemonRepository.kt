package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import retrofit2.Response

interface PokemonRepository {
    suspend fun getAllPokemons(): Response<PokemonList>

    suspend fun getPokemonDetails(pokemonName: String): Response<PokemonDetails>
}
