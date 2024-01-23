package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.database.PokemonDB
import com.zeyadsadaka.bamtest.database.PokemonEntityAdapter
import com.zeyadsadaka.bamtest.network.APIException
import com.zeyadsadaka.bamtest.network.AppAPI
import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import java.net.UnknownHostException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiClient: AppAPI,
    private val pokemonDB: PokemonDB,
    private val pokemonEntityAdapter: PokemonEntityAdapter,
) : PokemonRepository {
    override suspend fun getAllPokemons(): PokemonList {
        try {
            val pokemonList = apiClient.getPokemons()
            if (pokemonList.isSuccessful && pokemonList.body() != null) {
                pokemonDB.pokemonDao()
                    .insertAllPokemons(
                        pokemonEntityAdapter
                            .toPokemonEntityList(pokemonList.body()?.results)
                    )
                return pokemonList.body()!!
            } else {
                throw APIException()
            }
        } catch (e: UnknownHostException) {
            return pokemonEntityAdapter.toPokemonList(pokemonDB.pokemonDao().getAllPokemons())
        } catch (e: Exception) {
            throw APIException()
        }
    }

    override suspend fun getPokemonDetails(
        pokemonName: String
    ): PokemonDetails {
        try {
            val pokemonDetailsResponse =
                apiClient.getPokemonDetails(pokemonName)
            if (pokemonDetailsResponse.isSuccessful) {
                pokemonDetailsResponse.body()?.let {
                    pokemonDB
                        .pokemonDao()
                        .insertPokemon(
                            pokemonEntityAdapter.toPokemonEntity(it)
                        )
                    return it
                } ?: run {
                    throw APIException()
                }
            } else {
                throw APIException()
            }

        } catch (_: UnknownHostException) {
            return pokemonEntityAdapter.toPokemonDetails(
                pokemonDB.pokemonDao().getPokemon(pokemonName)
            )
        } catch (_: Exception) {
            throw APIException()
        }
    }
}