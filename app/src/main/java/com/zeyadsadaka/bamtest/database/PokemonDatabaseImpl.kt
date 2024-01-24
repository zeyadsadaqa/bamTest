package com.zeyadsadaka.bamtest.database

import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import javax.inject.Inject

class PokemonDatabaseImpl @Inject constructor(
    private val pokemonDB: PokemonDB,
    private val pokemonEntityAdapter: PokemonEntityAdapter,
) : PokemonDatabase {
    override suspend fun insertAllPokemons(pokemonList: List<Pokemon>?) {
        pokemonDB.pokemonDao()
            .insertAllPokemons(
                pokemonEntityAdapter
                    .toPokemonEntityList(pokemonList)
            )
    }

    override suspend fun getAllPokemons() =
        pokemonEntityAdapter.toPokemonList(pokemonDB.pokemonDao().getAllPokemons())

    override suspend fun insertPokemon(pokemonDetails: PokemonDetails) =
        pokemonDB
            .pokemonDao()
            .insertPokemon(
                pokemonEntityAdapter.toPokemonEntity(pokemonDetails)
            )


    override suspend fun getPokemon(pokemonName: String): PokemonDetails =
        pokemonEntityAdapter.toPokemonDetails(
            pokemonDB.pokemonDao().getPokemon(pokemonName)
        )

}