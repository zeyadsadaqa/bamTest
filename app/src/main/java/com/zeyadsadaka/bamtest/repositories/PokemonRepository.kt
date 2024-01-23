package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList

interface PokemonRepository {
    suspend fun getAllPokemons(): PokemonList

    suspend fun getPokemonDetails(pokemonName: String): PokemonDetails
}
