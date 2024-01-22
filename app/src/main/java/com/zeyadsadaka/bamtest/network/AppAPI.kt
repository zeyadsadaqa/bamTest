package com.zeyadsadaka.bamtest.network

import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import retrofit2.Response
import retrofit2.http.GET

interface AppAPI {
    @GET("pokemon?limit=20")
    suspend fun getPokemons(): Response<PokemonList>
}