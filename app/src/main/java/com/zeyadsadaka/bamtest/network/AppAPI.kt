package com.zeyadsadaka.bamtest.network

import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppAPI {
    @GET("pokemon?limit=20")
    suspend fun getPokemons(): Response<PokemonList>

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetails(@Path("pokemonName") pokemonName: String):
            Response<PokemonDetails>
}