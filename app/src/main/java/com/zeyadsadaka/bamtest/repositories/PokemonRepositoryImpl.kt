package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.network.AppAPI
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiClient: AppAPI
) : PokemonRepository {
    override suspend fun getAllPokemons() = apiClient.getPokemons()
    override suspend fun getPokemonDetails(pokemonName: String) =
        apiClient.getPokemonDetails(pokemonName)
}