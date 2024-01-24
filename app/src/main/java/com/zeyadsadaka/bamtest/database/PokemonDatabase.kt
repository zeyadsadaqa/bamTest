package com.zeyadsadaka.bamtest.database

import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList

interface PokemonDatabase {
    suspend fun insertAllPokemons(pokemonList: List<Pokemon>?)

    suspend fun getAllPokemons(): PokemonList

    suspend fun insertPokemon(pokemonDetails: PokemonDetails)

    suspend fun getPokemon(pokemonName: String): PokemonDetails
}